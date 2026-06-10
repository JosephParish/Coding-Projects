<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

/**
 * Model of Posts that are created by a user and belong in one group, having multiple possible comments and tags.
 */
class Post extends Model
{
    use HasFactory;

    protected $fillable = ['group_id', 'user_id', 'title', 'description', 'file_path', 'mime_type'];

    public function group()
    {
        return $this->belongsTo(Group::class);
    }

    public function user()
    {
        return $this->belongsTo(User::class);
    }

    public function comments()
    {
        return $this->hasMany(Comment::class);
    }

    public function tags()
    {
        return $this->belongsToMany(Tag::class, 'post_tag')->withTimestamps();
    }
    
}
