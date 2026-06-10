<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\Role;

class RoleSeeder extends Seeder
{
    public function run(): void
    {
        $roles = ['Mod', 'Group Owner', 'Web Mod', 'Web Admin'];

        foreach ($roles as $role) {
            Role::create(['role' => $role]);
        }
    }
}
