package com.project.demo.utils;

import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import org.springframework.util.ResourceUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Base64Util {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static Date date;
    private static StringBuffer stringBuffer;

    /*
     * @Author:  Yzl
     * @Param:   图片路径
     * @Desc:    图片转成Base64字符串
     * @return:  转换后的Base64字符串
     * */
    public static String imageToBase_64(String url) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(url);
            data = new byte[in.available()];
            int read = in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BASE64Encoder base64Encoder = new BASE64Encoder();

        return data == null ? "" : base64Encoder.encode(data);

    }

    /*
     * @Author:  YZL
     * @Param:   base64字符串
     * @Desc:    将Base64字符串转成图片，并存入路径
     * @retutn:  转换成功返回true,否则返回false
     * */
    public static boolean base64ToImage(String baseStr, String path) {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] bytes = base64Decoder.decodeBuffer(baseStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            OutputStream outputStream = new FileOutputStream(path);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * @Author:  YZL
     * @Desc:    将当前时间转成字符串作为图片名称返回
     * @retutn:  生成的图片名称
     * */
    public static String getNewImageNameByDate() {
        date = new Date();
        return format.format(date);
    }


    /*
     * @Author:  YZL
     * @Desc:   检查存储的文件夹是否存在，不存在则创建
     * @retutn:  path是否已经可用
     * */
    public static boolean checkPath(String path) {
        if (path == null) {
            return false;
        }
        path = path.trim();
        if (path.length() <= 0) {
            return false;
        }
        try {
            int index = -1;
            for (int i = path.length() - 1; i >= 0; --i) {
                if (path.charAt(i) == '/' || path.charAt(i) == '\\') {
                    index = i;
                    break;
                }
            }
            String dir = path.substring(0, index);
//            System.out.println(dir);
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /*
     * @Author:  YZL
     * @Param:  文件名
     * @Desc:   创建简单的相对路径文件
     * @retutn:  路径
     * */
    public static String createSimpleRelativePath(String dirName) throws BusinessException {
        date = new Date();
        String dir = format.format(date).substring(0, 8);
        stringBuffer = new StringBuffer("");
        stringBuffer.append(dirName).append("/").append(dir).append("/").append(getNewImageNameByDate()).append(".jpg");

        checkPath(stringBuffer.toString());
        return stringBuffer.toString();
    }


//    static String s = "iVBORw0KGgoAAAANSUhEUgAAAJ8AAACgCAYAAAASN76YAAAACXBIWXMAAAsTAAALEwEAmpwYAAAF+mlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDE5LTExLTIxVDE1OjE2OjMwKzA4OjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAxOS0xMS0yMVQxNToyMzowMyswODowMCIgeG1wOk1ldGFkYXRhRGF0ZT0iMjAxOS0xMS0yMVQxNToyMzowMyswODowMCIgZGM6Zm9ybWF0PSJpbWFnZS9wbmciIHBob3Rvc2hvcDpDb2xvck1vZGU9IjMiIHBob3Rvc2hvcDpJQ0NQcm9maWxlPSJzUkdCIElFQzYxOTY2LTIuMSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo5ODE0M2RjMC00ZDI3LTE2NDgtODJmMi05ZWY5N2RmYWI3NDEiIHhtcE1NOkRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDo1YTk0YTJhNi00M2E5LWQ3NGUtYjE2NS1mY2I1N2Y2ZWZiZjQiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDozNGU5NDk1MS0zZTQ3LTM3NDgtODc4Ni1iNzhjYTQ4NWE3NzIiPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjcmVhdGVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjM0ZTk0OTUxLTNlNDctMzc0OC04Nzg2LWI3OGNhNDg1YTc3MiIgc3RFdnQ6d2hlbj0iMjAxOS0xMS0yMVQxNToxNjozMCswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKFdpbmRvd3MpIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDo5ODE0M2RjMC00ZDI3LTE2NDgtODJmMi05ZWY5N2RmYWI3NDEiIHN0RXZ0OndoZW49IjIwMTktMTEtMjFUMTU6MjM6MDMrMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE5IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8L3JkZjpTZXE+IDwveG1wTU06SGlzdG9yeT4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4gfgXtAAAgIElEQVR4nO2de3BTd5bnP7YMlh/IWNiB2CBwwG1DAPNIAIEBdydLL1R2mJlim2UKpjeTpSt0MtXFpipMppPKpOjpLN2VZnY2mUylH5ks2aTJULNFVypsM4HYYFsQSICYYIwNxsI4EBu/H7KN7P3j3p98LSTdK+lePWx9qiikK+nea/urc37n/M7v/JJGR0cxioZOTO0DZHQOktozzJTeIUwjoySNjJI0CqMASZA0AiQDSUmMjkgvJI2Owo+W0qTn/fzkJBtWP0xSsRVXThp9U0wMj4ySPOQmZXgEU98wps5Bptzo5KVv+0nrGCS900V65yA5vcOkDbnJke+PkVFIku553P9TTfzD9FRa86dxa+40mvMzuTsjjY5sM90ZUxh4JIsRPX+meCbJSPHFIt/9iF1TTSSnJGEZGWXu8AjFwOJRmDuqEJYeJMuCFCQl8RZAEvQDvaYk7mZMxTk9lc7cNPoPbOCSPleODya8+DYeZqf8MAN4DFgErFX7XH4mzZlTGJiZQW9OGoPZZoZz0hiZNhW/v7CeIZKG3NA2gKlvmJRbPWR0D2L+dgBrh4sZKpc8CHwDNAHNABXbqdbyM8YrE1J8CsFNA7YAT/l7b34mzUtz+abYSv+MNEZz0jCZTSQDFGSxTo/7aeyiCsDlZqRtAHddOylX28k6d4clAT52E6gAPgWo2M77etxLLDGhxCeLLqDgirKpX/Uwd9flM6q3yIJBCBKguZfhL+9iPvsNs2/3MtvH21uBTwAH0DdRhBj34pMFlwHYkUSXq3w928y9786hfsVMXLMzmQLREZsWhCBr27l/0onVj2WsAY7J/8e1RUza8HtJfBXbo3wnQaKwctuA7ylfE4J7ci7D6Skkx6rYAiGE2NzL8PGbZJ1qZrmPt71HHLvluBOfLLp5wDPy/x42zObCfymmN14F5w+lRXz/CvN9uOaPgcMQXyKMG/EFEt2uRVQ+YZOyGhNJdL5o7KLK5WbkaAPmY4087vXySeAj4mRcGPPi8ye6/Eyady7i+kIrKTDxReeNsIZ/uI7p3+pZ4/VyXFjCmBWfIpD4SxR5uWwz9154jJrZmUyJhOCc3TjEY5cb9yiM9g09mOvLmCpNcphNmJTHbRbsRt6fEOEJJ6OHrlDq9fJ7wKexKsCYFJ8svCeBHyqP71tFxUIrKXqLTimwtgGGr3cxequH1GvtZDd2MdvlJiOU8+Zlcnuehba5FnrzMhkusmJKM0mWWm9RBhBhK/CPwM1YEuHGwzEmPkUE+xqKlMnelVSU5OrnXoXY2gYY/vwOpq9amfH1PYrDPa9WzCb61uRxdeVMeoqtpJhNmPQSoxDh7y6T7hUhnwTehdhwxTElPll4W5FSJ4AUvf7VYvohfNE5u3EMuLn/VSsjJ5qYVdtOUXh3rB9mE30rZtKw5RE6ZqbrI8bGLqqaexl+4zxLvKb2XiEGrGBMiE8xttuPbO30GtcJwX3mJPmPNylqd5Gj020bykIrdVse4ZtluaRCeC66sYsqH6446lYw6uKThbcC2CuObS7g3PYihkIVnXCpF1sZ/PAqhS295Otzt9FhbR6X/vN36MpJY0qoIhTpmf0OCrxyhM8SpbRMVMUnC28Pikj2Z6WUh2rtnN04XG7cJ50kfXh14qVdzCb6nlvOuXCsoR8reBD4MtICjIr4fLnZomzqX1rNtxD82E641g9qSS+/xUrdbzgGeX45FatnMRWCF6EYC75cSZni8Eng3UgKMOLiUySM94tjYnYiFNG53Ljfvcy06hZKdL7VuCBUEYqI+PWzPFTXQaF8+CZSMBKRcWBExScLbwnwojgWipsVY7oj9SQdbXggsz8peeExypflkhqKFfQxQxKRcWDExOcrafwv/1H69gUrvIutDL5xfpzLSIA0Jnx9PV8EG5g0dlFV2879A5+zUXH4x0CPkQKMiPhk4W1HLu7Mz6T570ulhUFahefsxtE2wPD+MyyKl3RJtCibwxfPLGYItLtiP+NAQ/OBGw9Li8YMw1t4RdnU/30pTQVZrAtGeJ80MrK3nA0J4alTfouVu45hb+phUDltGIiCLNbNzmTKPz3JacXh/cASxZIE3TFMfN7Ce3wWNS+t5ttgRHetg8qffEbhREydGM3LlZQdqScpGAGmp5D8z/9hnABfBOYZJUBDxOctvA2zufDcMrqDEd7FVgZ3H6c0Ye1C52gDa3YfZ+m1Diq1iLAgi3VmE8liPC6zH4MEqLv4FHO0HuH91WL6gxHeby8zNRFU6IPLTcbu45Q2dGpzw+Lv5EOA0/QWoK7iU0S120BytVqF5+zG4ezGse80tsmSLI4krzko+6SRkWAE6OWC/wmdBaib+BR5vB+CFFxodbUiYbzrGPZ4n4uNZT68yrq3L5EejAv2IUDd0EV8ipmLF0GqStEaXIg0yu7jD1ThJjCA6hZK9p3GFowAvaLgX+ll/fR0u54ps4NlXNUqvKYeBveWs0HH+0igQksv+buPs1SrANNTSN63igr5UC6wRw8Bhi0++SY8wvP6lvjF2Y2joZNBr8RmggghByJLr3VQqfbegizWLbSSsmuR571rgSfDFWBY4lOkVOaBtMZCy5pZYfFecySEF01cbjL++gTLtVrAJ2wkPT5L6pSANLYPKwUTsvgUAcZTAH9eyBkti3uE8BIWLzYQFlCrAJ9bRne2mXvyof0BP6BCOJYvAznAyM+k+U/m49YivLaBB+YQE0QZl5uMn3xGoVYBvr6eK4pDL4Zq/UISn3yxvxHPX7HTqCXAcLlxGxlcLLRSt6OYqq0LOGM102bUdSYi7S5yfn4WqxYBmk0k713pCUCWACtCEWBKsB9QrLuYB9I4z2xSP4+zG8dfn/DZ7CZs8jK5/XdruZFmIsVmkb4E2wpxtA1w5ZfnmZ/IHWqjtp2iI/Wc2VaII1BFjGxoqoqyqZeLUfcCu4K9XiiWL0O+GI/PokbrOO/nZ7GGuvg6EDuKqTqwHmdRNuuVvzCbBfuKmWw4sB7nC49Rrvd1JypHG1hz9g5DahawIIt1YumDzP5grV9Q4pNP/px4rmUGQ5REGbFO9mellG8pIDnQt9Rmwb4sl9Rfb6IyL5Pbet/DROTNC2xsG2BYiwtW5P/mEaT71Sw+RXS7xOuifhGRrRElUQfLODV3mrbScZsF+3eyKT2wHufWBZzR+14mIlrG5iL/V5RNvfhYMNcI1u16olst7tblxm1EZPvOJk6Hso7VZsG+rZDR55erf3ESwP/4nOwQ3O92rdZPk/gUZVKAtujW2Y3jV+fHt6jVg4NlnJIDi5AWUNss2FfPYuqr9sQ4UI2v71GsZfwH0ipE+eFTSP12VAnG8m0DqaOAaKTtD1EMqvc471U75eGs3BfYLNgXTCf1Z6UJAarx5gU2uty4A71HzH4oDmma+1UVn3ySp8VzLa0sXG7ceheDPr+cigXTg18e6A+bBfvcaQkBauFX58kNMvhYggbrp8XyZSA33FaYVr8Y4W7L5vDF6llM1bunnRBgIhUTmNp2itQqoUXwoZh6U535CCg++cM/EM+1dBZo6NTX3eZlcvuZxQwZ1eFTpGJ2FI8rG0/ghZYikIIs1r3wmKfwYB5evbO9UbN8QVu9A58/0KQ6LA6sx2l0a1mbBfuWApLX5k2uvc+CRctquNmZTFFYv2cCWT+/4pM/5NnFR83qObtxnL3DkJ6zGAfLOKXXudSwWbDvKZEaUSbwzdEG1gy4uR/oPcFYPzXL9xRos3ogRUZa3qeF55dToUdkGyzvbNJWDDtZ+ZfLWLRYP8XTzf6sn0/xKVahAdqs3ieN+u0juzaPS0YEGGrYLNjTTKQkxn/+qW6hpG2AYbX3KSLfteDbGwayfD8Ead2tlpvScwptTwn9kRaeQIz/zCb6onH9eOA3NTykJfJVHPJZcu9PfPPEg03z6Iqk1YvkOM8fNgv255ZzLtr3Eat8fY9itcQzSBMS8sNtvl5/QHyyQjeDtATSy3/7RC+rt3UBZ6IxzvPFslxSE9bPP/+nljQ16/ef5uNSHJrn/R5/lm8twH99lMtqeb2zd6R2XOFiNtG3rZDRWBAeSNbvzwq5GO37iFW0dJVITyE5P1PaNR34gbfr9SW+FeKB2HjFH85uHB/U8qiWm1XDmkZnrAhP8F2bfsOJiYjacKsgi3U/KOK6/PSBvYPHiU8Z5SpqtPzSNsCwXl2kXPdJ1drOK1KkaVgeMJn5v/UsU/ubeRmwFcrXvC1fBrJC/7SQFrVA4+h1/RLKiVZo8YfLTYaWtIvCkI2Ler3F94h44BUq+0TvblJq2fMEsce/XiNLLfD400Ja5KdLUOT8POKTFVkK2lxuUw+DId+xH3xtJRotxBKAaN9HrKNlGwovQ+YxcN6Wby3Aqoe5q5rbu0F2sDeqxvWu2BEfSK1lo30P8YCWL6ki6n1MHFOKz2MO1+Wri8CIjVdONDEr2kGH6AW9+zhLo3kf8cSnTUxTc72rH/aIz9NkXCk+T5pDrUxeyyAzFKK9DamyV6ARa4wnKlrG/k/O9WgmF9nQCV8suhCg6ELk/2LN0pZLRhCtoEOUhOlZmTOZUPu7paeMM2iPwPh2GUsASvNpL8jy/wdwduP4Y6NxLukzJ8lFuo8m/SNa8r5aTUGirUbofNXKiNrfLT+TZnnL1UUw5nY9LmZGmvp4z0iX9MebFEVq3Ces3e7jlCaEFx5q4/WCLNYtzeUb+elqYKewfJ7wV62QoKHT2PSDnGxWTfWEgxjbvXSalYmxnT7I4/WARmNpLv3HGgF5q1th+RaBVMWidpFrHepVLuGiZ4mWN8rttBLC0xe1QHSuZXx2JRkp2JgLsCQHp1p+7+K3xk+DaZkzDAVnN44j9RNzJ/JYoLk3sNHwDjrEkyUAcy3q9WuRSIdonTMMloZOBhN79BpHQydmtfcovOsj4/J5eZmBq1MjmQY5ep0MPa2fEcs6E4zn3B3mqAUdC6Z75nmLklG0NfDyyQ8QyblXI4oWEmM8Y9GSMSi20iU/nJUMzBAvePnkB1Dz6XqjV5U0xFbRwkRGzTsqvGtuMmDVeuKWXuMjXSUf1PJotOd6EwSH2pd82tSx1z3i05JmaeomM+y7C4J2Fzl6BR6ZU43dVT2BRN/9wN7RasYkHnvc7kNptKstFupwqUczevPOV8zUw/qZTZgSq9GMp8MV+HXl0M5j+SypqHwM7vaPjQ8jRW07RXpE2TYL9u8XqBdNJAiPYIZmnmg3Jy2w+JzdOPqHSQvz3kJCS38QLTz1SKJM32j674+5VTU84ktLUV+BHq1URXULJXpYP7MJU6INmrF0DQZn+QDImBLbVkEP62ezYH96MT163VOCBxlyqy88E8FtMn46CHmjpTeHkWjtjqSG2YQp0YXKONSCUmVQG1fpB7XuSFoQXagSuxFFn7gS39f3KNbD+tks2F9bS6Me95QgdOJKfAC/PM98vfJ+sdCObaKRnBR4hqOxa2zIkwzaEq9mk/YQ2khaeslv6gncll8LNgv2nDSmJLbC0pesVPVK9w6XlC/2WL6+4fhpiqPXYm6xFVYiANGPqSbtWZNkkFIPAxqSg7E0PfVJIyN6uF8RgCR2o9SHrFTtY3KP+NoGAofINgv29CkMhHlvuvHhVdbplf4Ru1EmEtDhk65hskKQDLQDdA+qFw3MTFevfIkkWvcE04LYhyMhwPDIywzO8t0D+HYAqzIS8UW2Wb34IJLUtlOkR/AhEAJcaKVOj/NNRmZnBs6guNxjJVcpwF0Yi0ACMddCb3WL2rsiy8uVlB3arF/Bqc2C/W9X4/j5Weqi2TvmVTvlC6aTKp63DTB89DoZei8viDRtA2Nu2TPm00IwJjWSvH2JdD0rnmUBtj86g6t6nVMrZhN9hzbjENu7in8rZrLhmcUMHdqMI5ajc7Wi3XsDY/vyetwuQL9KFaqaSY0W1S2U6Ol+QRLg36yiI9Iu+H89wQUhOF/3JKLzQ5txxOL4VC0f3NTtseat4yxfU3dg8WVMHbebdExhRCNHYQEjJcCDZZzSkswXItxTQv+r9tjZK1jLfPmtHk8hS/04S9bSG/gHTzOREku5Pm9+fhar3guOhACNtjKhbHRos2BfMJ3UQ5txWM20GXl/Wnh8FrcC3X9jF1UNneTJT1uE+GoArraTFejkNgv2gixPh8mYo7adorN3GDJCgHtK6C+bwxd6nleQl8ntUDc6FFbwf36X+mi74QXT1bMhisD2RjLwPkhupaGTPLV0y7KHov8NC8SbF9g44Oa+EQJ8ZjFDRsyE/N1aboS7AY74gkRzpqbIGthzesUUN4TluwHa0i2P5sRmxKvkR8dZb8R5xUyInsUIO4qp0muzGXF/0YqG1X4Or5iib5zbBWjuVWlzNW0s9xTLGDH+g7FihJ+V6jPQ31JAsp7bfoloePfSyG5arSUo+6qVdPnhTfBRz1fXrv4tjIXBrRq17RQdqSfJKAHOnUbqO5s4HU4AZlQ9oc2CfUN+ZDetfmIud9SCja9aeVh+ehl4Xym+aoDPv2FmoHGfzYL9+/PiY/rpaANrjAhAQPo9FGWz/teb+CqUcdaOYqqM3N5VWECjgiRvSnLVU0RyP2aAKzBm+d5HbkVb10Gh2klWzYruYqJgePMCG/VOQCsR46yDZZzSui5kbR6X9Ha3/u7tmcUMRWK9ilp+0ms4VwPj3a7nj6M205GTFtmGQeHyciVlRgtwxUw2HFiP82AZpwKlPJ5fTsWeEvojtb2rzYL9wHqcRl5Di+X/8q6naqpVHFOO7zxjF0cLSY+qxL1lc/ginia5X66kTA4SHEa6OpsFctJw7CmRmo53Dkpf5OmpJMtf2pDyeeFysIxTe8vZYMS5y2YzZLP4P3djF1Wf3aJYfnq2YjvvbzzMA8HFSeB7J53Me8JGlb/GQTYL9q3zOVV+S6e7jxCRECBIvx/5/5hAvh+HUQZDiydUpPHOimMet1uxnfeB8zBuYBjWBWORlyspu9hqnAuOVcT4T+/zagloLrWOW9dxUzzwTrV48n1eH/BJvK57eOM8ZXqtAYk39F6tt3U+fWoplo/qmC8/rVa+5qtEqgbgozrmq6Vcymbr/02KFB9eZZ3edYCxjs2CXUtKRCtWM21qHtDlZkThSStlDwt4iU9+4RhIrldZ8uyLnDSmxHPbieoWSnYfZ2ldB6cniwjNJkx6/c3+YiFfq42dq26PK8Mb1x/Rr+Xz8cEHsFmw7yg2dqsqo3G5yfjRcdY3dE6OcaDNgn2eRZ8ZqtWzAu8+2thF1cc3WCg/rfZ+3V9l8kmAj2+wULXKJTc+5nrVeM1B2W8vM3WiC9DZjePLuywI9zxaxvvNvQwrotxjSpcLPsQnv+EISOGxWqEB6D+IjRblt1i5+zhLv7zLqYkqwrYBhvVo8rmtkFG1QOP4zXH1oTe93+PP8vUgZ6KPNjBDLfBQM7/xhMtNxt5yNkzEaNjZjeOX5z2RZ8honS8+1cxy+eF7vl73KT7Z+r0PcO4OS9QCD5g41k/w4VXWTaRgROy2qce+wn+xMPD0YGMXVSec47pVfertciFwi7QvxYOjDZgnk/UTiGBElGbFqwid3TiaehjUY7fNrQs4o6X49dAVSuWHJ/29R20p5HsAxxq1bZj3wmOxs5JKT442sGbXMezxODMiNrbWa3XfU49wXy29Uts+boLiiC+rBwHEJ3/A84v+w3VMatZvWS6p8VBoGipvnKds32ls8RKQCOHpVVCwo5gqtdKpxi6q3vmKxfLTGgI0JVCzfH3AxwD/Vq++T63Ngv2VNVKh4ESlpZf8veVsELMjsSpCZzeOATf39axkUatBbOyiqrad+4r0ykf+rB6oiE/+4GHxXM36gTTrEe0lfJGguoWSXcewi6g4lkQoLJ6eC6m0lvwf+JyN8sMafKRXlGhtf3EEJOunFvmKJXwazxv3fHiVdbEkQmc3joutDOpp8dbmcUmt5L+xi6rTt8dp461AVg80iE8+wVHx/INaMtWsH0hdltTeM5GIBRGKdMob5/VtHfL0Ynq01D/+tsZjaavR0Os7mMY/74GUOFQrsxdtHCaD+/VGiDDS6RlnN463L5GuRzpFyat2yrUEGYfrxqXa/rea1QON4pNP9CnyrMdPK1mkZv0mm/v1RqRn3r5EupHRsbMbx7UOKncfZ2l1CyV6nrtsDl+IVm2B3udyM6JIx72Hxh0ONFs+WYD/CNKc7wkno1rc72Tf66K6hZK95WzYdxrbH65Tfq2DSr2E6OzG0dDJ4O7jlOq9KaPZRN8zixlSE15jF1X7HRQoDvmczfBFsP32biKXxhy6QqmW4COx14VESy/5b5ynbPdxSt++RPrp25SH45ad3TiO1JP0mkP/1nAw1icw0HvENJqiWPQXwVwjqB4hYtURsBbg4HlmvbTa/0IjGFu88mUel/R2C/FKdQslor1w2Ry+2DqfU6IiWO0P7uzG4XLj3neK4nYXOUbcnzzOUy2Vc7kZOXRlXJBRo9XqQZDiU/AL4MW6DgpPOLkbaKUbeMZ/jpvd3NZjYnsiUX6LlWIV4EIrdcseomrVLNy+xCjSKHpHs0qeX06FlnGeD3f7djDCgxDEJ1u/nUhKX3voCqX2PPWmNPLiZceuYwnx+aO2naLadoo+lDtBm030FWRRl23GlZvOQEMH2V/f86x/1Z2yOXyhpU+gD3f7SijXC6nHsqzwt8VzLdGv4NebqAzlmpMRl5uM2naKqlsoOdrAGiOFt9BKndYAo/8+I15VKzeDtXoQ/q6Tr4AU/f7uMula0i9mE6Z3NkW2fVeCwORlcvtvV9OudSH9jz8dN233bijCgzDEJ1/wJorks5b0i82CPc1EymRPwcQKeZncPrAepxbhNXZR9dNK5ioOPRvOtcOyfIrkcw1I6ZfmXoa1CDAnjSkJAUaXYIV3uI6pXmmVvlCtHuiw2bN8cU9+5+VKylxuRhICjG0WWqkLRngnnIwqZjE+Jsi0ii/03NTFY4Kf/XdtpTxCgIkxYGRZm8clrWO8xi6qmnsZVgQYNcDhcIUHOolPvpE+FCH33nKKtUTAYgwYK3tJTHR2FFOltT+gEJ6iBL8V+IUewgMdLZ8iAPkFSBHwTyuZq1WAsbKXxETmhcco19oRVaRUvNZ+/He9hAf6ul0hwBrgIEj9Xl4/y0Nac4CiEiaWN7aLVw6WcWpZrvrMBUjCc7kZ8UqphBXZ+kL3jfxkAX6JnIKp66AwWAFuKSA5EYjow0IrdYc249DafFwIz2vc/mPCjGx9YcgukooUjEeAWl0wjAUihzbjiMa2oxOF55dTIQKLYFytD+H16C08MEh88KAAb/cyO1gBim1HJ+p6YKOwmml7ZxOng9nPTQQXXq7WMOEBJG34vdTVoGK7EacHuQjhSeCHANlm7r2+nitmE8mBKmGUiDKidy8zLVGWFZjnl1MR7CaCYsmjYuUZGCy8jYcNtHwChQU8CFIU/Oy/s77/vnoiWmCzYP9ONqV7SujXa9upicZCK3W/3kRlKMI74WTUS3jPYqDwBIZbPoFsAecB+8WxvSupKMklRasFBMkKApy9w9CbF8b9wiYlVjNtr6zhSii7GTV2UfXWRSzn7rBEPnQTOVdrtPA2Ho6g+OQL7gQygH8WxzYXcG57EUPBCBDGRPhJIyN6r9iKB8wm+vat4pyWwk9vRET70mkWKboLVBNCQWioRFx88kV3yg9fBOkbF8o4UCDGgyedJE0GEVrNtD29mMtac3be+BnfvUcQC3/0ICriU1x8J7AV2CaO7VtFxUJrcG5YICzhxVYG37rA43qv5oo2C63U/Wgpd0PdLFCMr393mXRF00aQ3GxIxaDhEFXxyTfwwDiwKJv6l1bzLUAoIoSxXiX/eo2seI+OdxRTtaVACgxD3TXJxxwtRHB854uoi0++iQfcMIRnBQVKa/jJDR6ubacovLs1HrOJvu8XUFM2myGtK9r8Iazd4TqmevVYjLib9SYmxKe4mZ3ARuC/iWNF2dT/ZCV30lOCHwt6I4TY1MPgp01MO9NCcay45rxMbm+czU2xak2PfeH8jO1agVeJQBpFjZgSH4yLhp9DYQX/vJAzfzJf2uM3XBEKRKDS3MP9i62Yz91hTqSWdcpLJNv8LZEMBxHJ7ndQ4LWHXtStnZKYE59AFuESJFfsYdciKp+wSRvT6CVCgbJzQFMPg3f6SLrVQ+q1drLv9jMjmAXaZhN91jQ651lom2uhNy+T4dmZni1PAf3EJhCi+6CWTK+AogZ4CwMKA8IhZsUH48aC4yJiGEtOg/4i9IW/lhYut2SNfXVxitSeukJ0Rxswe43rWoF30aHc3QhiWnwChSv+AfA95WtGWsJYR1Sg/P7qA5YO4DdARSyKThAX4hMoRPiXyL1iBGKWBCa2CEX02tzL8KGvya/roNDrLe8hzaNHJX0SDHElPoHCHT+NlyXMz6R55yKuL7RGziVHAuFaq26TpFjIo+Q3IHUCi3XRCeJSfAKFCD3lWkoen0XN1gXcm50pDfLjTYhCcOfuwP9rfCByBSlJ/BHymul4EZ0grsUnUIhwCbAZRYpG8Pgsar5no70gi+RQ5o8jgXCp/fcZcbSQdNLJPB+CA2nN7CfEQK4uHDYeDr1FWsyg/ANsPEwN0rjQDmwBckHaP+7cHek92WburXmYc0tz6RfuGSJvGYXYXG5G6toZqbyNVVHa5E01UEmcWjl/xL3l84fCIq4A/gxpDtknRdnUP5rDvcJsBudaSE5PGSuyDVeUSpG1DeCuayelqZuM653k+rFsgpPAZ8h7WUwUwQkmhNvVgpdrXgSsRraKgcg2c++hNNotqbhy0nClpeDOmML9nDQpvzdVzu4NuaF7iOQhN0l9w6S0DZA6cJ+Ull6y+++TpqiZC0QN0qq/s8hbRk00wSkZJ75Jhkjb5AILgEIgjwDWUWdqgCbgCpJlE/uTTVix+SLux3wh4uuPLKyjEKUVmAOY5cd5itf80YrUNqRH/tcO3APuAjfk94htAiaV0Hzx/wGC7VaKMJ4jSAAAAABJRU5ErkJggg==";
//
//    public static void main(String[] args) {
//        String path = createSimpleRelativePath("img");
//        System.out.println(path);
//        checkPath(path);
//        base64ToImage(s, path);
//    }

    private static String getJarPath() throws BusinessException {
        try {
            String path = ResourceUtils.getURL("classpath:").getPath();
            return path;
        } catch (Exception e) {
            throw new BusinessException(EmBusinessErr.ROOT_JAR_PATH_ERROR);
        }
    }


    public static Boolean saveImg(Map<String, String> imgMap) {
        try {
            for (Map.Entry<String, String> entry : imgMap.entrySet()) {
                Base64Util.base64ToImage(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
