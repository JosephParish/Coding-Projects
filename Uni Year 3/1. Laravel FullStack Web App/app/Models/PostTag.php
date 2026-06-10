<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Relations\Pivot;

/**
 * Pivot table to model many to many relationship of Posts and Tags,
 * via both having one to many to this pivot table.
 */
class PostTag extends Pivot
{
    protected $table = 'post_tag';
    protected $fillable = ['post_id', 'tag_id'];
}
