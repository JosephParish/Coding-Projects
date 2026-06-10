<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

/**
 * Model of Groups containing many members/users, many posts and a single owner.
 */
class Group extends Model
{
    use HasFactory;

    protected $fillable = ['owner_id', 'name', 'description'];

    public function owner()
    {
        return $this->belongsTo(User::class, 'owner_id');
    }

    public function users()
    {
        return $this->belongsToMany(User::class)->withPivot('role_id')->withTimestamps();
    }

    public function posts()
    {
        return $this->hasMany(Post::class);
    }
}
