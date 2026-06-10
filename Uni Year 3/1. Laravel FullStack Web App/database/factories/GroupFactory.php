<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;
use App\Models\User;

class GroupFactory extends Factory
{
    public function definition(): array
    {
        $topics = [
            'AI', 'Machine Learning', 'Cloud Computing', 'Cybersecurity',
            'Data Science', 'Quantum Computing', 'Web Development',
            'DevOps', 'Blockchain', 'IoT', 'AR/VR', 'Software Engineering'
        ];

        $verbs = [
            'Exploring', 'Understanding', 'Building', 'Mastering',
            'Scaling', 'Debugging', 'Automating', 'Revolutionizing',
            'The Future of', 'A Deep Dive into'
        ];

        $name = $this->faker->randomElement($verbs) . ' ' . $this->faker->randomElement($topics);

        $sentences = [
            'Technology continues to evolve, shaping how we live and work every day.',
            'Developers are constantly finding new ways to optimize performance and scalability.',
            'Artificial intelligence and automation are redefining modern industries.',
            'Cybersecurity remains a critical priority as systems become more interconnected.',
            'Cloud platforms now offer unprecedented flexibility for global development teams.',
            'Innovation in data science is transforming business decision-making worldwide.',
            'As open source communities grow, collaboration drives faster software innovation.',
            'New breakthroughs in hardware and software are bridging the gap between humans and machines.',
        ];

        $description = implode(' ', $this->faker->randomElements($sentences, rand(2, 4)));

        return [
            'name' => $name,
            'description' => $description,
            'owner_id' => User::inRandomOrder()->first()?->id ?? User::factory(),
        ];
    }
}
