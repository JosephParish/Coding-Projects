<?php

namespace App\Http\Controllers;

use App\Services\RecommendationService;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use App\Models\Post;
use App\Models\Group;
use App\Models\Role;

class PostController extends Controller
{    
    private function canManagePost(Post $post)
    {
        $user = auth()->user();

        // Post owner
        if ($user->id === $post->user_id) {
            return true;
        }

        // Group owner
        if ($user->id === $post->group->owner_id) {
            return true;
        }

        // A group moderator || ownership role passed onto somone else
        $groupRole = $post->group->users()->where('user_id', $user->id)->first()?->pivot?->role_id;

        if ($groupRole === Role::where('role', 'Mod')->value('id')) {
            return true;
        }
        if ($groupRole === Role::where('role', 'Group Owner')->value('id')) {
            return true;
        }

        // Web Mod or Web Admin
        $userHasWebRole = $user->groups()->wherePivotIn('role_id', Role::whereIn('role', ['Web Mod', 'Web Admin'])->pluck('id'))->exists();

        if ($userHasWebRole) {
            return true;
        }

        return false;
    }

    protected $recommendationService;

    public function __construct(RecommendationService $recommendationService)
    {
        $this->recommendationService = $recommendationService;
    }

    public function index() 
    {
        $recommendations = $this->recommendationService->getRecommendations(auth()->id());
        $posts = Post::with('group')->latest()->paginate(10);
        return view('posts.index', compact('posts','recommendations'));
    }

    public function show(Post $post) 
    {
        $post->load(['user', 'comments.user']);
        return view('posts.show', compact('post'));
    }

    public function create() 
    {
        $groups = Group::all();
        return view('posts.create', compact('groups'));
    }

    public function edit(Post $post) 
    {
        if (! $this->canManagePost($post)) {
            abort(403, 'Unauthorized');
        }
        $groups = Group::all();
        return view('posts.edit', compact('post', 'groups'));
    }

    public function store(Request $request)
    {
        $validated = $request->validate([
            'group_id' => 'required|exists:groups,id',
            'title' => 'required|max:255',
            'description' => 'required|string',
            'file_path' => 'nullable|file|mimes:jpg,jpeg,png,pdf,docx|max:2048',
        ]);

        $validated['user_id'] = auth()->id();
    
        if ($request->hasFile('file_path')) {
            $file = $request->file('file_path');
            $validated['file_path'] = $file->store('post_files', 'public');
            $validated['mime_type'] = $file->getClientMimeType();
        }

        Post::create($validated);

        return redirect()->route('posts.index')->with('success', 'Post created!');
    }

    public function update(Request $request, Post $post)
    {
        if (! $this->canManagePost($post)) {
            abort(403, 'Unauthorized');
        }

        $validated = $request->validate([
            'group_id' => 'required|exists:groups,id',
            'title' => 'required|max:255',
            'description' => 'required|string',
            'file' => 'nullable|file|mimes:jpg,jpeg,png,pdf,docx|max:2048',
        ]);

        $validated['user_id'] = auth()->id();

        if ($request->hasFile('file')) {
            $file = $request->file('file');
            $validated['file_path'] = $file->store('post_files', 'public');
            $validated['mime_type'] = $file->getClientMimeType();
        }

        $post->update($validated);

        return redirect()->route('posts.index')->with('success', 'Post updated successfully!');
    }

    public function destroy(Post $post)
    {
        if (! $this->canManagePost($post)) {
            abort(403, 'Unauthorized');
        }

        $post->delete();

        return redirect()->route('posts.index')->with('success', 'Post deleted successfully!');
    }
}