<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Relations\Pivot;

/**
 * Pivot table to model many to many relationship of Groups and Users,
 * via both having one to many to this pivot table.
 * 
 * Also connects users to their roles in each group.
 */
class GroupUser extends Pivot
{
    protected $table = 'group_user';
    protected $fillable = ['user_id', 'group_id', 'role_id'];

    public function role()
    {
        return $this->belongsTo(Role::class);
    }
}
