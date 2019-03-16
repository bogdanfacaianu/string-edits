$(document).ready(function () {
  $("#words").on('keyup', function () {
    let words = this.value.match(/\S+/g).length;
    let wordsLimit = 50;

    if (words > wordsLimit) {
      let trimmed = $(this).val().split(/\s+/, wordsLimit).join(" ");
      $(this).val(trimmed + " ");
    } else {
      $('#display_count').text(words);
      $('#word_left').text(wordsLimit - words);
    }
  });
});