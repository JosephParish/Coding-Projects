@extends('layouts.app')

@section('title', 'Posts')

@section('header', 'Posts')

@section('content')
<div class="bg-white dark:bg-gray-800 p-6 text-white shadow rounded">
    
    {{-- Recommended Posts --}}
    <h2 class="p-2 flex justify-between mb-4">Recommended Posts</h2>
    <ul>
    @foreach ($recommendations as $rec)
        <li>
            <a href="{{ route('posts.show', $rec['post_id']) }}">
                {{ $rec['title'] }}
            </a>
        </li>
    @endforeach
    </ul>

    
    {{-- All Posts --}}
    <div class="p-6 flex justify-between mb-4">
        <h2 class="text-xl font-bold">All Posts</h2>
        <a href="{{ route('posts.create') }}" class="px-4 py-2 bg-blue-600 rounded">Create Post</a>
    </div>
    @forelse ($posts as $post)
        <div class="mb-4 p-4 border rounded bg-gray-700">
            <h3 class="text-xl font-bold text-white">
                    {{ $post->title ?? 'Untitled Post' }}
            </h3>
            <p class="text-gray-300">
                <strong>Group:</strong> {{ $post->group->name ?? 'No Group' }}
            </p>
            <p class="text-gray-200 mt-1"> <strong>Description:</strong> {{ Str::limit($post->description, 100) }}</p>
            <h3 class="text-gray-300">Tags:</h3>
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
                    <button onclick="return confirm('Are you sure?')" class="text-red-600 hover:underline">Delete</button>
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

</div>
@endsection
