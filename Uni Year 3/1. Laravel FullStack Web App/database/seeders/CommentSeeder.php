<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\User;
use App\Models\Post;
use App\Models\Comment;

class CommentSeeder extends Seeder
{
    public function run(): void
    {
        Post::all()->each(function ($post) {
            Comment::factory(5)->create([
                'post_id' => $post->id,
                'user_id' => User::inRandomOrder()->first()->id,
            ]);
        });
    }
}
