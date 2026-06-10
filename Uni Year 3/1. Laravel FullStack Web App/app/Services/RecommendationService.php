<?php

namespace App\Services;

use App\Services\RecommendationService;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;
use App\Models\Tag;
use App\Models\User;
use App\Models\Post;
use App\Models\Group;
use App\Models\Role;

class RecommendationService
{

    public function getRecommendations(int $userId): array
    {
        $user = User::with(['ownedGroups', 'groups'])->findOrFail($userId);

        $userGroupIds = array_unique(array_merge(
            $user->ownedGroups->pluck('id')->toArray(),
            $user->groups->pluck('id')->toArray()
        ));

        $posts = Post::with(['tags', 'group'])->get();

        $popularTags = Tag::withCount('posts')->orderBy('posts_count', 'desc')->limit(5)->pluck('tag')->toArray();

        $scores = [];

        foreach ($posts as $post) {
            $score = 0;

            if (in_array($post->group_id, $userGroupIds)) {
                $score += 3;
            }

            $score += strlen($post->description ?? '') / 1000;

            $postTagNames = $post->tags->pluck('tag')->toArray();
            $commonTags = array_intersect($popularTags, $postTagNames);
            $score += count($commonTags) * 2;

            $scores[] = [
                'post_id' => $post->id,
                'title' => $post->title,
                'score' => $score
            ];
        }

        usort($scores, fn($a, $b) => $b['score'] <=> $a['score']);

        return array_slice($scores, 0, 3);
    }
}