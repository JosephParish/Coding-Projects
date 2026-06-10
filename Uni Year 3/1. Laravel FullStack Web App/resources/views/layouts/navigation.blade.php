<nav x-data="{ open: false }" class="bg-white dark:bg-gray-800 border-b border-gray-200 dark:border-gray-700">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between h-16">

            <!-- Left side -->
            <div class="flex">
                <a href="{{ route('home') }}" class="flex items-center shrink-0">
                    <x-application-logo class="block h-9 w-auto text-gray-800 dark:text-gray-200" />
                </a>

                <div class="hidden space-x-8 sm:flex sm:ms-10">
                    <x-nav-link :href="route('home')" :active="request()->routeIs('home')">
                        Home
                    </x-nav-link>

                    <x-nav-link :href="route('groups.index')" :active="request()->routeIs('groups.*')">
                        Groups
                    </x-nav-link>

                    <x-nav-link :href="route('posts.index')" :active="request()->routeIs('posts.*')">
                        Posts
                    </x-nav-link>
                </div>
            </div>

            <!-- Right side -->
            <div class="hidden sm:flex sm:items-center">

                @auth
                    <!-- User Dropdown -->
                    <x-dropdown align="right" width="48">
                        <x-slot name="trigger">
                            <button
                                class="inline-flex items-center px-3 py-2 text-sm border border-transparent rounded-md text-gray-500 dark:text-gray-300 hover:text-gray-700 dark:hover:text-gray-100">
                                {{ Auth::user()->name }}

                                <svg class="ms-1 h-4 w-4" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                    <path fill-rule="evenodd" d="M5.23 7.21a1 1 0 0 1 1.4-.02L10 10.58l3.37-3.39a1 1 0 1 1 1.44 1.39l-4.08 4.1a1 1 0 0 1-1.42 0l-4.08-4.1a1 1 0 0 1-.02-1.4z"/>
                                </svg>
                            </button>
                        </x-slot>

                        <x-slot name="content">
                            <x-dropdown-link :href="route('profile.show', auth()->user())">
                                Profile
                            </x-dropdown-link>
                            <x-dropdown-link :href="route('profile.edit')">
                                Edit Profile
                            </x-dropdown-link>

                            <form method="POST" action="{{ route('logout') }}">
                                @csrf
                                <x-dropdown-link :href="route('logout')"
                                    onclick="event.preventDefault(); this.closest('form').submit();">
                                    Log Out
                                </x-dropdown-link>
                            </form>
                        </x-slot>
                    </x-dropdown>
                @endauth

                @guest
                    <a href="{{ route('login') }}"
                       class="px-3 py-2 text-sm text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white">
                        Login
                    </a>
                    <a href="{{ route('register') }}"
                       class="px-3 py-2 text-sm text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white">
                        Register
                    </a>
                @endguest

            </div>
        </div>
    </div>
</nav>