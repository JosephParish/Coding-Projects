@extends('layouts.app')

@section('title', 'Create Post')
@section('header', 'Create Post')

@section('content')
<div class="bg-white dark:bg-gray-800 p-6 shadow rounded max-w-lg mx-auto">

    <form action="{{ route('posts.store') }}" method="POST" enctype="multipart/form-data">
        @csrf

        <label class="block mb-2 text-gray-800 dark:text-gray-100">Title</label>
        <input name="title" class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black" required>

        <label class="block mb-2 text-gray-800 dark:text-gray-100">Description</label>
        <textarea name="description" class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black" required></textarea>

        <label class="block mb-2 text-gray-800 dark:text-gray-100">Group</label>
        <select name="group_id" class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black" required>
            @foreach ($groups as $group)
                <option value="{{ $group->id }}">{{ $group->name }}</option>
            @endforeach
        </select>

        <label class="block mb-2 text-gray-800 dark:text-gray-100">File (optional)</label>
        <input type="file" name="file_path" class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black">

        <button class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">
            Create Post
        </button>
    </form>

</div>
@endsection