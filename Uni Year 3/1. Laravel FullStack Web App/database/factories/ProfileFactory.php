<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;
use App\Models\User;

class ProfileFactory extends Factory
{
    public function definition(): array
    {
        $sentences = [
            'I love exploring new technologies and frameworks.',
            'In my free time, I contribute to open source projects.',
            'I enjoy mentoring junior developers and sharing knowledge.',
            'Passionate about building scalable and maintainable software.',
            'I am constantly learning about cloud computing and DevOps.',
            'AI and data science fascinate me, and I like experimenting with them.',
            'Collaboration and teamwork are at the core of my work ethic.',
            'I enjoy attending tech meetups and hackathons to connect with peers.',
            'My goal is to write clean, efficient, and well-documented code.',
            'I love solving complex problems and optimizing systems for performance.',
        ];

        $bio = implode(' ', $this->faker->randomElements($sentences, rand(2, 4)));

        return [
            'user_id' => User::inRandomOrder()->first()?->id ?? User::factory(),
            'bio' => $bio,
            'avatar' => 'avatars/default.png',
        ];
    }
}
