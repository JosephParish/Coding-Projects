<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

class TagFactory extends Factory
{
    public function definition()
    {
        $topics = [
            'AI', 'Machine Learning', 'Cloud Computing', 'Cybersecurity',
            'Data Science', 'Quantum Computing', 'Web Development',
            'DevOps', 'Blockchain', 'IoT', 'AR/VR', 'Software Engineering'
        ];

        return [
            'tag' => $this->faker->unique()->randomElement($topics),
        ];
    }
}
