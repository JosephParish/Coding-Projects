@extends('layouts.app')

@section('title', 'Edit Profile')
@section('header', 'Edit Profile')

@section('content')
<div class="bg-white dark:bg-gray-800 p-6 shadow rounded max-w-lg mx-auto">
    <form action="{{ route('profile.update') }}" method="POST" enctype="multipart/form-data">
        @csrf
        @method('PATCH')

        <label class="block mb-2 text-gray-800 dark:text-gray-100">Name</label>
        <input type="text" name="name" value="{{ $user->name }}" class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black" required>

        <label class="block mb-2 text-gray-800 dark:text-gray-100">Email</label>
        <input type="email" name="email" value="{{ $user->email }}" class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black" required>
        
        <label class="block mb-2 text-gray-800 dark:text-gray-100">Bio</label>
        <input type="text" name="bio" value="{{ $user->profile->bio ?? 'theres not much to know..'}}" class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black" required>

        <label class="block mb-2 text-gray-800 dark:text-gray-100">Avatar</label>
        <input type="file" name="avatar" class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black">

        @if ($user->avatar)
            <p class="mb-4">Current Avatar:</p>
            <img src="{{ asset('storage/' . $user->avatar) }}" class="w-full mb-4 p-2 border rounded bg-white text-black dark:bg-gray-100 dark:text-black">
        @endif

        <button class="px-4 py-2 bg-blue-600 text-white rounded">Save Changes</button>
    </form>
</div>
@endsection