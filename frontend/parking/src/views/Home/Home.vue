<template>
  <div>
    <h1>Type something in the search bar to find a parking slot </h1>
      <div class="car">This is the best project in the world</div>
  </div>
</template>

<script>

export default {
  name: "Home",
  methods: {
  },
};
</script>

<style scoped>

@font-face {
    font-family: 'Rocher';
    src: url(https://assets.codepen.io/9632/RocherColorGX.woff2);
}

.car {
    font-family: 'Rocher';
    text-align: left;
    height: 50px;
    width: 100%;
    font-size: 32px;
    animation: car-move 10s linear infinite;
    animation-delay: 0s;
    position: absolute;
    bottom: 3rem;
    left: 0;
    color: red;

}

@keyframes car-move {
    0% {
        left: -100px;
    }
    100% {
        left: 100vw;
    }
}
</style>