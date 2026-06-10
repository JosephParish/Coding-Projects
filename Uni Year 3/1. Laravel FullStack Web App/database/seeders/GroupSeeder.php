<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use App\Models\User;
use App\Models\Role;
use App\Models\Group;

class GroupSeeder extends Seeder
{
    public function run(): void
    {
        $roleOwner    = Role::where('role', 'Group Owner')->first();
        $roleMod      = Role::where('role', 'Mod')->first();
        $roleWebMod   = Role::where('role', 'Web Mod')->first();
        $roleWebAdmin = Role::where('role', 'Web Admin')->first();

        $webAdminUser  = User::where('email', 'webadmin@example.com')->first();
        $webModUser    = User::where('email', 'webmod@example.com')->first();
        $ownerUser     = User::where('email', 'groupowner@example.com')->first();
        $modUser       = User::where('email', 'mod@example.com')->first();

        Group::factory(5)->create()->each(function ($group) use ($roleOwner, $roleMod, $roleWebMod, $roleWebAdmin, $webModUser, $webAdminUser, $ownerUser, $modUser) 
        {
            $group->owner_id = $ownerUser->id;
            $group->save();

            $group->users()->attach($ownerUser->id, ['role_id' => $roleOwner->id]);
            $group->users()->attach($webModUser->id, ['role_id' => $roleWebMod->id]);
            $group->users()->attach($webAdminUser->id, ['role_id' => $roleWebAdmin->id]);
            $group->users()->attach($modUser->id, ['role_id' => $roleMod->id]);

            $randomUsers = User::whereNotIn('id', [
                $ownerUser->id, $webModUser->id, $webAdminUser->id, $modUser->id
            ])->inRandomOrder()->take(2)->get();

            foreach ($randomUsers as $user) {
                $group->users()->attach($user->id, ['role_id' => $roleMod->id]);
            }
        });
    }
}
