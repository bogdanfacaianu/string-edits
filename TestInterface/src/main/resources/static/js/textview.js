function getMatches() {
  let languageName = document.getElementsByName("languageName")[0].value;
  let word = document.getElementsByName("word")[0].value;
  let action_src = "@{/getMatches/" + languageName + "/" + word;
  let your_form = document.getElementById('single-word-matches');
  your_form.action = action_src;
}