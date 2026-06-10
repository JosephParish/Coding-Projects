<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\Post;
use App\Models\Group;
use App\Models\User;
use App\Models\Tag;

class PostSeeder extends Seeder
{
    public function run(): void
    {
        Group::all()->each(function ($group) {
            Post::factory(3)->create();
        });
    }
}
