<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

/**
 * Model of the roles that users can have in a group. (related by pivot table of users in groups).
 */
class Role extends Model
{
    use HasFactory;

    protected $fillable = ['role'];

    public function groupMemberships()
    {
        return $this->hasMany(GroupUser::class);
    }
}
