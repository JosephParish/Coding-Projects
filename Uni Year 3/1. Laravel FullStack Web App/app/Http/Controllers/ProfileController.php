<?php

namespace App\Http\Controllers;

use App\Http\Requests\ProfileUpdateRequest;
use Illuminate\Http\RedirectResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Redirect;
use Illuminate\View\View;
use App\Models\User;
use App\Models\Post;
use App\Models\Profile;

class ProfileController extends Controller
{
    public function show(User $user)
    {
        $groups = $user->groups()->get();
        $posts = $user->posts()->latest()->paginate(10, ['*'], 'posts_page');
        $comments = $user->comments()->latest()->paginate(10, ['*'], 'comments_page');
        $profile = $user->profile;
        return view('profile.show', compact('user', 'groups', 'posts', 'comments'));
    }

    public function edit()
    {
        $user = Auth::user();
        $profile = $user->profile;
        return view('profile.edit', compact('user'));
    }

    public function update(Request $request)
    {
        $user = Auth::user();
        $profile = $user->profile;

        $validated = $request->validate([
            'name' => 'required|string|max:255',
            'email' => 'required|email|unique:users,email,' . $user->id,
            'bio' => 'nullable|string|max:500',
            'avatar' => 'nullable|image|max:2048',
        ]);

        if ($request->user()->isDirty('email')) {
            $request->user()->email_verified_at = null;
        }

        if ($request->hasFile('avatar')) {
            $validated['avatar'] = $request->file('avatar')->store('avatars', 'public');
        }

        $user->update([
            'name' => $validated['name'],
            'email' => $validated['email'],
        ]);

        $user->profile()->update(
            ['bio' => $validated['bio'] ?? 'theres not much to know...']
        );

        return redirect()->route('profile.show', $user)->with('success', 'Profile updated successfully!');
    }

    public function destroy()
    {
        $user = Auth::user();
        Auth::logout();
        $user->delete();

        return redirect('/')->with('success', 'Account deleted.');
    }
}