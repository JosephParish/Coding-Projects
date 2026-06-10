@extends('layouts.app')

@section('title', 'Create Group')
@section('header', 'Create Group')

@section('content')
<div class="bg-white dark:bg-gray-800 p-6 shadow rounded max-w-lg mx-auto">

    <form action="{{ route('groups.store') }}" method="POST">
        @csrf

        <label class="block mb-2 text-gray-800 dark:text-gray-100">Name</label>
        <input name="name" class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black" required>
        
        <label class="block mb-2 text-gray-800 dark:text-gray-100">Description</label>
        <textarea name="description" class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black" required></textarea>

        <button class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">
            Create
        </button>
    </form>

</div>
@endsection