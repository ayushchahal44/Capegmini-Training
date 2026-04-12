let like = document.getElementById("likeBtn");
let disLike = document.getElementById("dislikeBtn");

let likeCount=0;
let disLikeCount=0;
function increaseLike(){
    likeCount++;
    console.log("Liked. Total count:" +likeCount);
    like.innerText = `❤️ ${likeCount}`;
}

function decreaseLike(){
    disLikeCount++;
    console.log("UnLiked. Total count:" +disLikeCount);
    disLike.innerText = `👎 ${disLikeCount}`;
}

