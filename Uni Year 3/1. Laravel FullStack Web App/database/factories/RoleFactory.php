<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

class RoleFactory extends Factory
{
    public function definition(): array
    {
        $roles = [
            'Mod', 'Group Owner', 'Web Mod', 'Web Admin'
        ];

        return [
            'role' => $this->faker->unique()->randomElement($roles),
        ];
    }
}
