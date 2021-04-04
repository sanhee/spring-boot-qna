$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e){
    e.preventDefault();
    console.log("click me");

    var queryString = $(".answer-write").serialize();
    console.log("query : "+queryString);

    var url = $(".answer-write").attr("action");
    console.log(url);

    $.ajax({
            type : 'post',
            url : url,
            data : queryString,
            dataType : 'json',
            error: onError,
            success : onSuccess,
    });
}

function onError(){

}

function onSuccess(data, status){
    //TODO. [추후공부] Jackson 라이브러리 : Java Object를 JSON으로 변환하거나 JSON을 Java Object로 변환하는데 사용할 수 있는 Java 라이브러리
    // org.springframework.boot:spring-boot-starter-web 에 포함된 내장 라이브러리
    // data : RestController를 통해 Json으로 리턴한 데이터 ( Answer 클래스 안의 Getter 멤버 )
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.replyAuthor, data.replyTime, data.replyContents, data.answerId, data.answerId);
    $(".qna-comment-slipp-articles").prepend(template);
    $(".answer-write textarea").val("");
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};
