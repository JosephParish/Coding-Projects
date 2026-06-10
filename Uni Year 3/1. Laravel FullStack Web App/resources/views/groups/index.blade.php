@extends('layouts.app')

@section('title', 'Groups')

@section('header', 'Groups')

@section('content')
<div class="bg-white dark:bg-gray-800 p-6 text-white shadow rounded">
    <div class="flex justify-between items-center mb-4">
        <h2 class="text-lg font-bold">All Groups</h2>
        <a href="{{ route('groups.create') }}" class="px-4 py-2 bg-blue-600 text-white rounded">
            Create Group
        </a>
    </div>

    {{-- Group Details --}}
    <table class="w-full text-left border-collapse">
        <thead>
            <tr>
                <th class="py-2">Name</th>
                <th class="py-2">Description</th>
                <th class="py-2">Actions</th>
            </tr>
        </thead>

        <tbody>
            @foreach ($groups as $group)
            <tr class="border-t">
                <td class="py-2">{{ $group->name }}</td>
                <td>{{ $group->description }}</td>
                <td class="py-2">
                    <a href="{{ route('groups.show', $group) }}" class="text-blue-600">View</a> |
                    <a href="{{ route('groups.edit', $group) }}" class="text-yellow-600">Edit</a> |
                    <form action="{{ route('groups.destroy', $group) }}" method="POST" class="inline">
                        @csrf
                        @method('DELETE')
                        <button onclick="return confirm('Are you sure?')" class="text-red-600 hover:underline">Delete</button>
                    </form>
                </td>
            </tr>
            @endforeach
        </tbody>
    </table>

    
    <div class="mt-4">
        {{ $groups->links() }} {{-- Laravel outputs pagination controls --}}
    </div>
</div>
@endsection