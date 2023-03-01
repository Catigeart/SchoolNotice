export function timeFormatter(row, column, cellValue, index) {
    let format = 'YYYY-mm-dd HH:MM:SS'
    let date = new Date(cellValue);
    const dataItem = {
      'Y+': date.getFullYear().toString(),
      'm+': (date.getMonth() + 1).toString(),
      'd+': date.getDate().toString(),
      'H+': date.getHours().toString(),
      'M+': date.getMinutes().toString(),
      'S+': date.getSeconds().toString(),
    };
    Object.keys(dataItem).forEach((item) => {
      const ret = new RegExp(`(${item})`).exec(format);
      if (ret) {
        format = format.replace(ret[1], ret[1].length === 1 ? dataItem[item] : dataItem[item].padStart(ret[1].length, '0'));
      }
    });
    return format
  }

  //将num左补0为len长度的字符串
function lpadNum(num, len) {
    var l = num.toString().length;
    while(l < len) {
        num = "0" + num;
        l++;
    }
    return num;
}
//将传入的Date格式化为"yyyyMMdd HH:mm:ss.SSS"
export function formatDate(date){
    const d = new Date(date)
    var year = d.getFullYear();
    var month = d.getMonth() + 1;
    var day = d.getDate();
    var hours = d.getHours();
    var minutes = d.getMinutes();
    var seconds = d.getSeconds();
    var milliSeconds = d.getMilliseconds();
    var resStr = year + lpadNum(month, 2) + lpadNum(day, 2) + " " + lpadNum(hours,2) + ":" + lpadNum(minutes,2) + ":" + lpadNum(seconds,2) + "." + lpadNum(milliSeconds, 3);
    return resStr;
}