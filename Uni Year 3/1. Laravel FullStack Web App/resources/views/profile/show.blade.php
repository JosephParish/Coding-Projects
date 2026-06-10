@extends('layouts.app')

@section('title', $user->name)
@section('header', $user->name)

@section('content')
<div class="bg-white dark:bg-gray-800 p-6 text-white shadow rounded">

    {{-- Bio --}}
    <h2 class="text-2xl font-bold mb-4">{{ $user->name }}</h2>
    <p class="text-gray-600 dark:text-gray-300 mb-6">Member since: {{ $user->created_at->format('F Y') }}</p>
    <p class="text-white mb-4">Bio: {{ $user->profile->bio ?? "theres not much to know..."}}</p>
    <p class="space-y-2 mb-4">
            @if ($groups->count())
                <h3 class="text-xl font-semibold mb-4">Group Roles</h3>
            
                <ul class="space-y-2 mb-4">
                    @foreach ($groups as $group)
                        @php
                            $role = \App\Models\Role::find($group->pivot->role_id);
                        @endphp
            
                        <li class="text-gray-300 text-sm">
                            <strong class="text-blue-400">{{ $group->name }}</strong>
                            — {{ $role->role ?? 'No role' }}
                        </li>
                    @endforeach
                </ul>
            @else
                <p class="text-gray-400 mb-6">No authority yet.</p>
            @endif
    </p>

    {{-- POSTS --}}
    <h3 class="text-xl font-bold mb-3">Posts by {{ $user->name }}</h3>

    @if ($posts->count())
        <ul class="space-y-2 mb-4">
            @foreach ($posts as $post)
                <li>
                    <a href="{{ route('posts.show', $post) }}" class="text-blue-400 hover:text-blue-300">{{ $post->title }}</a>
                    <span class="text-gray-400 text-sm">— {{ $post->created_at->diffForHumans() }}</span>
                </li>
            @endforeach
        </ul>
        {{ $posts->links() }}
    @else
        <p class="text-gray-400 mb-6">No posts yet.</p>
    @endif

    {{-- COMMENTS --}}
    <h3 class="text-xl font-bold mt-8 mb-3">Comments by {{ $user->name }}</h3>

    @if ($comments->count())
        <ul class="space-y-4 mb-4">
            @foreach ($comments as $comment)
                <li class="">
                    <a href="{{ route('posts.show', $comment->post) }}" class="text-gray-300">{{ $comment->comment }}</a>
                    <span class="text-gray-400 text-sm">— {{ $comment->created_at->diffForHumans() }}</span>
                </li>
            @endforeach
        </ul>
        {{ $comments->links() }}
    @else
        <p class="text-gray-400">No comments yet.</p>
    @endif
</div>
@endsection