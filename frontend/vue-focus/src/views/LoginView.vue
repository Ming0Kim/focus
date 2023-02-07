<template>
  <section class="bg-gray-50">
    <div
      class="absolute inset-0 flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0"
    >
      <div
        class="w-full bg-white/80 rounded-lg shadow-lg md:mt-0 sm:max-w-md xl:p-0"
      >
        <!-- # 로고 -->
        <a
          href="#"
          class="flex justify-center items-center my-10 text-4xl font-black text-blue-900"
        >
          <img
            class="w-8 h-auto mr-2"
            src="../assets/blue_opacity.png"
            alt="logo"
          />
          FOCUS
        </a>
        <!-- # 로고 -->
        <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
          <h2
            class="font-bold leading-tight tracking-tight text-gray-900 md:text-xl"
          >
            할당받은 계정을 입력하세요
          </h2>
          <form
            @submit.prevent="login()"
            class="space-y-4 md:space-y-6"
            action="#"
          >
            <div>
              <label
                for="loginId"
                class="block mb-2 text-sm font-medium text-gray-900"
                >ID</label
              >
              <input
                v-model="loginId"
                type="text"
                id="loginId"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5"
                placeholder="ID"
                required=""
              />
            </div>
            <div>
              <label
                for="loginPw"
                class="block mb-2 text-sm font-medium text-gray-900"
                >Password</label
              >
              <input
                v-model="loginPw"
                type="password"
                id="loginPw"
                placeholder="••••••••"
                class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-600 focus:border-blue-600 block w-full p-2.5"
                required=""
              />
            </div>
            <div class="flex items-center justify-between">
              <!-- <div class="flex items-start">
                <div class="flex items-center h-5">
                  <input
                    id="remember"
                    aria-describedby="remember"
                    type="checkbox"
                    class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300"
                    required=""
                  />
                </div>
                <div class="ml-3 text-sm">
                  <label for="remember" class="text-gray-500"
                    >Remember me</label
                  >
                </div>
              </div>
              <a
                href="#"
                class="text-sm font-medium text-blue-600 hover:underline"
                >Forgot password?</a
              > -->
            </div>
            <button
              type="submit"
              class="w-full text-white bg-blue-600 hover:bg-blue-700 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
            >
              Sign in
            </button>
            <!-- <p class="text-sm font-light text-gray-500">
              Don’t have an account yet?
              <a href="#" class="font-medium text-blue-600 hover:underline"
                >Sign up</a
              >
            </p> -->
          </form>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const loginId = ref('');
const loginPw = ref('');

const router = useRouter();
const store = useStore();

const login = () => {
  store
    .dispatch('login', {
      id: loginId.value,
      password: loginPw.value,
    })
    .then(() => {
      // router.push({ name: 'Service' });
    })
    .catch(err => {
      console.log(err.message);
    });
};
</script>

<style lang="scss" scoped></style>
