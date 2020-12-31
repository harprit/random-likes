window.twttr = (function (d, s, id) {
  var js,
    fjs = d.getElementsByTagName(s)[0],
    t = window.twttr || {};
  if (d.getElementById(id)) return t;
  js = d.createElement(s);
  js.id = id;
  js.src = "https://platform.twitter.com/widgets.js";
  fjs.parentNode.insertBefore(js, fjs);

  t._e = [];
  t.ready = function (f) {
    t._e.push(f);
  };

  return t;
})(document, "script", "twitter-wjs");

document.getElementById("getTweet").addEventListener("submit", getTweet);

function getTweet(e) {
  e.preventDefault();

  document.getElementById("showTweet").innerHTML =
    '<div id="spinner" class="spinner-border text-primary" role="status">  <span class="sr-only">Loading...</span></div>';

  const username = document.getElementById("username").value;

  // validate
  if (!username) {
    document.getElementById("showTweet").innerHTML = "Please enter a username.";
    return;
  }

  const apiGatewayUrl =
    "https://65rc8jquhi.execute-api.us-east-1.amazonaws.com/u/";

  const restUrlToHit = apiGatewayUrl + username;

  fetch(restUrlToHit)
    .then(res => {
      const status = res.status;
      res.text().then(text => showTweet(status, text))
    })
    .catch(error => {
      console.error("Error in fetch:", error);

      // display error message
      document.getElementById("showTweet").innerHTML =
        "Sorry, something's wrong. Please try again later.";
    });
}

function showTweet(status, id) {
  if (status !== 200) {
    // then show the error message received from lambda as it is
    document.getElementById("showTweet").innerHTML = id;
    return;
  }

  // finally fetch tweet from Twitter
  window.twttr.widgets
    .createTweet(id, document.getElementById("showTweet"), {
      theme: "light"
    })
    .then(() => {
      // adjust view when showing a tweet
      document.getElementById("spinner").remove();
      document.getElementById("button").innerHTML = "Show Another";
    })
}