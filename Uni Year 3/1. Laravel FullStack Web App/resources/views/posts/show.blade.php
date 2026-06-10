@extends('layouts.app')

@section('title', $post->name)

@section('header', $post->name)

@section('content')
<div class="bg-white dark:bg-gray-800 p-6 text-white shadow rounded">

    {{-- Post Details --}}
    <p class="text-gray-700 dark:text-gray-300 mb-4">
        <strong>Group:</strong>
        <a href="{{ route('groups.show', $post->group) }}" class="text-blue-600">
            {{ $post->group->name }}
        </a>
    </p>

    <h2 class="p-2 text-lg font-bold">Title: {{ $post->title }}</h2>
    <h3 class="p-2 text-lg font-bold">Description: {{ $post->description }}</h3>
    <h3 class="p-2 text-lg font-bold">Tags:</h3>
    @foreach ($post->tags as $tag)
        <ul>
            <h3 class="text-lg font-bold">{{ $tag->tag }}</h3>
        </ul>
    @endforeach
    @if(str_contains($post->mime_type, 'image'))
        <img src="{{ Storage::url($post->file_path) }}" alt="Post image">
    @else
        <a href="{{ Storage::url($post->file_path) }}" target="_blank">Download file</a>
    @endif
    <span class="text-gray-400">by <a href="{{ route('profile.show', $post->user) }}" class="text-yellow-400 hover:underline">{{ $post->user->name ?? 'Unknown Author' }}</a></span>

    <a href="{{ route('posts.edit', $post) }}"
       class="inline-block mb-8 px-4 py-2 bg-yellow-600 text-white rounded">
        Edit Post
    </a>

    {{-- Comments Section --}}
    <h3 class="text-xl font-bold mb-4">Comments</h3>

    <div id="commentsList" class="text-gray-700 dark:text-gray-300 mb-4">
        @forelse ($post->comments->sortByDesc('created_at') as $comment)
            <div class="border-l-4 border-blue-600 bg-gray-100 dark:bg-gray-700 p-4 mb-4 rounded">
                <div class="flex justify-between">
                    <span class="font-semibold text-gray-800 dark:text-gray-200">
                        <a href="{{ route('profile.show', $comment->user) }}">{{ $comment->user->name }}</a>
                    </span>

                    <span class="text-sm text-gray-500">
                        {{ $comment->created_at->diffForHumans() }}
                    </span>
                </div>

                <p class="mt-2 text-gray-700 dark:text-gray-300">
                    {{ $comment->comment }}
                </p>

                <div class="mt-2 flex justify-end space-x-2">
                    <a href="{{ route('comments.edit', $comment) }}" class="text-yellow-400 hover:underline">Edit</a> |
                    <form action="{{ route('comments.destroy', $comment) }}" method="POST" class="inline">
                        @csrf
                        @method('DELETE')
                        <button onclick="return confirm('Are you sure?')" class="text-red-600 hover:underline">Delete</button>
                    </form>
                </div>
            </div>
        @empty
            <p class="text-gray-500">No comments yet. Be the first!</p>
        @endforelse
    </div>
    

    {{-- New Comment Form --}}
    <div class="mt-8">
        <h4 class="text-lg font-semibold mb-2">Add a Comment</h4>
    
        <form id="commentForm" action="{{ route('comments.store') }}" method="POST">
            @csrf
            <input type="hidden" name="post_id" value="{{ $post->id }}">
    
            <textarea name="comment" rows="3"
                class="w-full p-3 border rounded focus:ring focus:ring-blue-400 dark:bg-gray-900 dark:border-gray-600"
                placeholder="Write a comment..." required></textarea>
    
            <button class="mt-3 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">
                Submit Comment
            </button>
        </form>
    </div>
    
    {{-- AJAX Script --}}
    <script>
        document.getElementById('commentForm').addEventListener('submit', async function (e) {
            e.preventDefault();
                
            const form = e.target;
            const formData = new FormData(form);
                
            const response = await fetch(form.action, {
                method: "POST",
                headers: {
                    "Accept": "application/json"
                },
                body: formData
            });
        
            if (!response.ok) {
                const text = await response.text();
                console.error("Error response:", text);
                alert("Failed to submit comment");
                return;
            }
        
            const data = await response.json();
            const editUrl = `/comments/${data.id}/edit`;
            const deleteUrl = `/comments/${data.id}`;
        
            const newCommentHtml = `
                <div class="border-l-4 border-blue-600 bg-gray-100 dark:bg-gray-700 p-4 mb-4 rounded" id="comment-${data.id}">
                    <div class="flex justify-between">
                        <span class="font-semibold text-gray-800 dark:text-gray-200">
                            <a href="${data.user_profile_url}">${data.username}</a>
                        </span>
        
                        <span class="text-sm text-gray-500">${data.created_at_human}</span>
                    </div>
        
                    <p class="mt-2 text-gray-700 dark:text-gray-300">
                        ${data.comment}
                    </p>
        
                    <div class="mt-2 flex justify-end space-x-2">
                        <a href="${editUrl}" class="text-yellow-400 hover:underline">Edit</a> |
                        <form action="${deleteUrl}" method="POST" class="inline delete-comment-form">
                            <input type="hidden" name="_token" value="{{ csrf_token() }}">
                            <input type="hidden" name="_method" value="DELETE">
                            <button onclick="return confirm('Are you sure?')" class="text-red-600 hover:underline">
                                Delete
                            </button>
                        </form>
                    </div>
                </div>
            `;
        
            document.getElementById('commentsList').insertAdjacentHTML('afterbegin', newCommentHtml);
        
            form.reset();
        });
    </script>

</div>
@endsection