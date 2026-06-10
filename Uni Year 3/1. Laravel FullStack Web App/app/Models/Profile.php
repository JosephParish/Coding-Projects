<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

/**
 * Model of profiles each user has one of.
 */
class Profile extends Model
{
    use HasFactory;

    protected $fillable = ['user_id', 'bio', 'avatar'];

    public function user()
    {
        return $this->belongsTo(User::class);
    }
}
