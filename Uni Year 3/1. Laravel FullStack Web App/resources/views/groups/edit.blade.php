@extends('layouts.app')

@section('title', 'Edit Group')
@section('header', "Edit Group: $group->name")

@section('content')
<div class="bg-white dark:bg-gray-800 p-6 shadow rounded max-w-lg mx-auto">
    <form action="{{ route('groups.update', $group) }}" method="POST">
        @csrf
        @method('PUT')

        <label class="block mb-2 text-gray-800 dark:text-gray-100">Name</label>
        <input type="text" name="name" value="{{ $group->name }}" class="w-full mb-4 p-2 border rounded" required>

        <label class="block mb-2 text-gray-800 dark:text-gray-100">Description</label>
        <textarea name="description" class="w-full mb-4 p-2 border rounded" required>{{ $group->description }}</textarea>

        <button class="px-4 py-2 bg-blue-600 text-white rounded">Save Changes</button>
    </form>
</div>
@endsection