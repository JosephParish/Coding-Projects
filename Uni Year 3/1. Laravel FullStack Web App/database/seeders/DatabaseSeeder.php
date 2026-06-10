<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\User;

class DatabaseSeeder extends Seeder
{
    use WithoutModelEvents;

    public function run(): void
    {
        $this->call([
            RoleSeeder::class,
            UserSeeder::class,
            ProfileSeeder::class,
            GroupSeeder::class,
            PostSeeder::class,
            TagSeeder::class,
            CommentSeeder::class,
        ]);
    }
}
