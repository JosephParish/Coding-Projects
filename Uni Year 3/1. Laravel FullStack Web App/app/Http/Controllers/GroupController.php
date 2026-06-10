<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Group;
use App\Models\Post;
use App\Models\Role;

class GroupController extends Controller
{
    private function canManageGroup(Group $group)
    {
        $user = auth()->user();

        // Group owner
        if ($user->id === $group->owner_id) {
            return true;
        }

        // A group moderator || ownership role passed onto somone else
        $groupRole = $group->users()->where('user_id', $user->id)->first()?->pivot?->role_id;

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

    public function index()
    {
        $groups = Group::paginate(10);
        return view('groups.index', compact('groups'));
    }

    public function show(Group $group)
    {
        $posts = $group->posts()->with('user')->latest()->paginate(10);
        return view('groups.show', compact('group', 'posts'));
    }

    public function create() {
        $groups = Group::all();
        return view('groups.create', compact('groups'));
    }

    public function store(Request $request)
    {
        $validated = $request->validate([
            'name' => 'required|max:255',
            'description' => 'required|string',
        ]);
    
        $validated['owner_id'] = auth()->id();

        $group = Group::create($validated);
        
        $roleOwner = Role::where('role', 'Group Owner')->first();
        $group->users()->attach(auth()->id(), ['role_id' => $roleOwner->id]);

        return redirect()->route('groups.index')->with('success', 'Group created!');
    }

    public function edit(Group $group)
    {
        if (! $this->canManageGroup($group)) {
            abort(403, 'Unauthorized');
        }

        return view('groups.edit', compact('group'));
    }

    public function update(Request $request, Group $group)
    {
        if (! $this->canManageGroup($group)) {
            abort(403, 'Unauthorized');
        }

        $validated = $request->validate([
            'name' => 'required|max:255',
            'description' => 'required|string',
        ]);

        $group->update($validated);

        return redirect()->route('groups.show', $group)->with('success', 'Group updated successfully!');
    }

    public function destroy(Group $group)
    {
        if (! $this->canManageGroup($group)) {
            abort(403, 'Unauthorized');
        }

        $group->delete();

        return redirect()->route('groups.index')->with('success', 'Group deleted successfully!');
    }
}
