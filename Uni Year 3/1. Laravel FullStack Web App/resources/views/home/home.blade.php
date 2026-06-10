@extends('layouts.app')

@section('title', 'Home')

@section('content')
<div class="relative min-h-[80vh] flex items-center justify-center bg-cover bg-center text-white"
     style="background-image: url('/images/research.jpg');">

    <div class="absolute inset-0 bg-black/40"></div>

    <div class="relative z-10 text-center px-6">
        <h1 class="text-5xl md:text-6xl font-extrabold mb-6 drop-shadow-lg">
            Welcome to ResRe
        </h1>

        <p class="text-xl md:text-2xl mb-8 max-w-2xl mx-auto text-gray-200">
            A Research Repository where users share knowledge through topic-based groups and posts.
        </p>

        <a href="{{ route('groups.index') }}"
           class="inline-block bg-blue-600 hover:bg-blue-700 text-white px-8 py-4 text-lg rounded-lg font-semibold shadow-lg transition">
            Explore Groups
        </a>
    </div>
</div>
@endsection