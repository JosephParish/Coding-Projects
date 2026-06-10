<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

/**
 * Model of comments users can create many of and posts can have many of.
 */
class Comment extends Model
{
    use HasFactory;

    protected $fillable = ['user_id', 'post_id', 'comment'];

    public function user()
    {
        return $this->belongsTo(User::class);
    }

    public function post()
    {
        return $this->belongsTo(Post::class);
    }
}