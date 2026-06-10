<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\User;
use Illuminate\Support\Facades\Hash;

class UserSeeder extends Seeder
{
    public function run(): void
    {
        $users = [
            [
                'name' => 'Web Admin User',
                'email' => 'webadmin@example.com',
                'password' => Hash::make('password123'),
            ],
            [
                'name' => 'Web Mod User',
                'email' => 'webmod@example.com',
                'password' => Hash::make('password123'),
            ],
            [
                'name' => 'Group Owner User',
                'email' => 'groupowner@example.com',
                'password' => Hash::make('password123'),
            ],
            [
                'name' => 'Mod User',
                'email' => 'mod@example.com',
                'password' => Hash::make('password123'),
            ],
        ];

        foreach ($users as $userData) {
            User::updateOrCreate(['email' => $userData['email']], $userData);
        }

        User::factory()->count(6)->create();
    }
}
