@extends('layouts.app')

@section('title', 'Group Details')

@section('header', $group->name)

@section('content')
<div class="bg-white dark:bg-gray-800 p-6 text-white shadow rounded">
    
    {{-- Group Details --}}
    <h2 class="text-lg font-bold">Group Details</h2>
    <ul class="mt-2">
        <li><strong>Name:</strong> {{ $group->name }}</li>
        <li><strong>Description:</strong> {{ $group->description }}</li>
    </ul>

    {{-- POSTS --}}
    <h2 class="text-lg font-bold mt-6">Posts in this Group</h2>
    <ul class="list-disc ms-6 mt-2">
        @forelse ($posts as $post)
        <div class="mb-4 p-4 border rounded bg-gray-700">
            <h3 class="text-xl font-bold text-white">
                    {{ $post->title ?? 'Untitled Post' }}
            </h3>
            <p class="text-gray-300">
                <strong>Group:</strong> {{ $post->group->name ?? 'No Group' }}
            </p>
            <p class="text-gray-200 mt-1"> <strong>Description:</strong> {{ Str::limit($post->description, 100) }}</p>
            <p class="text-gray-300">Tags:</p>
            <ul class="space-y-4 mb-4">
                @foreach ($post->tags as $tag)
                    <li class="">
                        <a class="text-gray-300">{{ $tag->tag }}, </a>
                    </li>
                @endforeach
            </ul>
            <span class="text-gray-400">by <a href="{{ route('profile.show', $post->user) }}" class="text-yellow-400 hover:underline">{{ $post->user->name ?? 'Unknown Author' }}</a></span>
            <div class="mt-2 flex justify-end space-x-2">
                <a href="{{ route('posts.show', $post) }}" class="text-blue-600">View</a> |
                <a href="{{ route('posts.edit', $post) }}" class="text-yellow-400 hover:underline">Edit</a> |
                <form action="{{ route('posts.destroy', $post) }}" method="POST" class="inline">
                    @csrf
                    @method('DELETE')
                    <button onclick="return confirm('Are you sure?')" class="text-red-400 hover:underline">Delete</button>
                </form>
            </div>
        </div>
        @empty
            <p class="text-gray-400">No posts yet.</p>
        @endforelse

        {{-- Pagination --}}
        <div class="mt-4">
            {{ $posts->links() }}
        </div>
    </ul>

    <a href="{{ route('posts.create', ['group_id' => $group->id]) }}"
       class="inline-block mt-4 px-4 py-2 bg-blue-600 text-white rounded">
        Add Post
    </a>

</div>
@endsection