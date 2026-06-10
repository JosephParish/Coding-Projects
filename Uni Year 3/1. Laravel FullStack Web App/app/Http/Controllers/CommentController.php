<?php

namespace App\Http\Controllers;

use App\Models\Comment;
use App\Models\Post;
use Illuminate\Http\Request;
use App\Models\Role;

class CommentController extends Controller
{
    private function canManageComment(Comment $comment)
    {
        $user = auth()->user();

        // Comment owner
        if ($user->id === $comment->user_id) {
            return true;
        }

        // Post owner
        if ($user->id === $comment->post->user_id) {
            return true;
        }

        // Group owner
        if ($user->id === $comment->post->group->owner_id) {
            return true;
        }

        // A group moderator || ownership role passed onto somone else
        $groupRole = $comment->post->group->users()->where('user_id', $user->id)->first()?->pivot?->role_id;

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

    public function edit(Comment $comment)
    {
        if (! $this->canManageComment($comment)) {
            abort(403, 'Unauthorized');
        }

        $posts = Post::all();

        return view('comments.edit', compact('comment', 'posts'));
    }

    public function store(Request $request)
    {
        $validated = $request->validate([
            'post_id' => 'required|exists:posts,id',
            'comment' => 'required|string|max:2000',
        ]);

        $comment = Comment::create([
            'post_id' => $validated['post_id'],
            'comment' => $validated['comment'],
            'user_id' => auth()->id(),
        ]);

        return response()->json([
            'id'               => $comment->id,
            'comment'          => e($comment->comment),
            'username'         => $comment->user->name,
            'user_profile_url' => route('profile.show', $comment->user),
            'created_at_human' => $comment->created_at->diffForHumans(),
        ]);
    }

    public function update(Request $request, Comment $comment)
    {
        if (! $this->canManageComment($comment)) {
            abort(403, 'Unauthorized');
        }

        $validated = $request->validate([
            'comment' => 'required|string|max:2000',
            'post_id' => 'required|exists:posts,id',
        ]);

        $comment->update([
            'comment' => $validated['comment'],
            'post_id' => $validated['post_id'],
        ]);

        return redirect()->route('posts.show', $comment->post)->with('success', 'Comment updated successfully!');
    }

    public function destroy(Comment $comment)
    {
        if (! $this->canManageComment($comment)) {
            abort(403, 'Unauthorized');
        }

        $comment->delete();

        return redirect()->route('posts.show', $comment->post)->with('success', 'Comment deleted successfully!');
    }
}