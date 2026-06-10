<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

/**
 * Model of tags that posts can have. (related via pivot table).
 */
class Tag extends Model
{
    use HasFactory;

    protected $fillable = ['tag'];

    public function posts()
    {
        return $this->belongsToMany(Post::class, 'post_tag');
    }
}
