<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\Tag;
use App\Models\Post;

class TagSeeder extends Seeder
{
    public function run(): void
    {
        Tag::factory()->count(10)->create();

        Post::all()->each(function ($post) {
            $tagIds = Tag::inRandomOrder()->take(rand(1, 4))->pluck('id');
            $post->tags()->attach($tagIds);
        });
    }
}
