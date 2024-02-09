/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { useAuthStore } from '@/stores/authStore'
import { createRouter, createWebHistory } from 'vue-router/auto'

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
})

router.beforeEach((to, from) => {
  const authStore = useAuthStore();

  if (!authStore.isLoggedIn && to.path !== '/login' && to.path !== '/register') {
    return { path: '/login' };
  }
})

export default router;
