@extends('layouts.app')

@section('header', "Edit Comment")

@section('content')
<div class="bg-white dark:bg-gray-800 p-6 shadow rounded max-w-lg mx-auto">
    <form action="{{ route('comments.update', $comment) }}" method="POST">
        @csrf
        @method('PUT')

        <input type="hidden" name="post_id" value="{{ $comment->post_id }}">
        
        <label for="comment" class="block mb-2 text-gray-800 dark:text-gray-100">Comment</label>
        <textarea name="comment" id="comment" rows="4"class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black"required>{{ old('comment', $comment->comment) }}</textarea>

        <button type="submit" class="px-4 py-2 bg-blue-600 text-white rounded">Update Comment</button>
    </form>
</div>
@endsection