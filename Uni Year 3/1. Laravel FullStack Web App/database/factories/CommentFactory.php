<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;
use App\Models\User;
use App\Models\Post;

class CommentFactory extends Factory
{
    public function definition(): array
    {
        $sentences = [
            'Great post! Really enjoyed reading this.',
            'This is very informative, thanks for sharing!',
            'I have a question about one of your points.',
            'Could you provide more details on this topic?',
            'This is exactly what I was looking for.',
            'I disagree with one aspect, can we discuss?',
            'Fantastic explanation, very clear and concise.',
            'I learned something new today, thanks!',
            'Interesting perspective, never thought of it that way.',
            'Looking forward to your next post!',
            'Can anyone clarify this part for me?',
            'This could really help beginners in this field.',
            'I love how you broke this down step by step.',
            'Not sure I agree, but good points overall.',
            'This sparked a new idea for my own project!',
        ];

        $comment = $this->faker->randomElement($sentences);

        return [
            'user_id' => User::inRandomOrder()->first()?->id ?? User::factory(),
            'post_id' => Post::inRandomOrder()->first()?->id ?? Post::factory(),
            'comment' => $comment,
        ];
    }
}
