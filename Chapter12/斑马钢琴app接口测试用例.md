# 斑马钢琴app接口测试用例

背景说明：斑马钢琴app登陆，注册，线上课程，ai课程等主要功能模块接口测试；测试的用例参数存储在数据库中，通过myBatis+mysq获取到接口参数，发送接口
获取返回结果并进行验证，expected的值存储在数据库中，取返回重要字段的值进行验证。

整个项目在测试环境测试：[http://test.bluecard1000.com:8888/ ]()

## 注册登陆

### login

`http://dev.inner.doyure.com/xxxx6666/login`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|未登陆状态|code=1000 msg="用户未登陆"|
|case_02|新注册用户|code=0 score=null|
|case_03|认证完成用户，修改信用分数为666|code=0 score=666|

### credit_scores/calc

`api/credit_scores/calc?keyfrom=wap`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|未登陆状态|302重定向到登陆页面|
|case_02|新注册用户|code=0 msg="决策引擎已经开始计算"|
|case_03|新注册用户，重复请求|code=0 msg="决策引擎已经开始计算, 无需重复提交"|

## 公告

`api/notices`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|正常get|校验code=0|

## 身份证

### idcard_info

`api/authentications/idcard_info.json?keyfrom=wap`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|get获取身份证信息：|||
|case_01|未登陆状态|code=1000 msg="用户未登陆"|
|case_02|已登陆 未提交过身份信息|code=0 data=null|
|csae_03|已经登陆 刚上传身份证正反面|校验code=0|

`api/authentications/idcard_info`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|post上传身份证信息：|||
|case_04|身份证首次提交|code=0 对比上传身份证的信息real_name,idcard,native_place|
|case_05|身份证信息已经被注册|code=1 errors="此身份证已被注册"|

## 上传身份证信息

### PostIdcard

`api/authentications/idcard`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|正面：|||
|case_01|上传正常的身份证|code=0|
|case_02|眼睛遮挡的身份证|预期不给过，实际给过 code=0|
|case_03|身份证号码被遮挡|code=1 errors="请确保上传清晰完整的身份证照片"|
|case_04|身份证号码被p过|code=1 errors="请确保上传清晰完整的身份证照片"|
|case_05|身份证姓名被遮挡|预期不给过，实际给过 post的code=0和post之后的get code=0（因为post只是缓存）|
|反面：|||
|case_06|上传正常的身份证|code=0|
|case——07|日期遮挡|预期不给过，实际 code=0|

## 开户银行列表接口
### banks

`api/bank_codes.json?method=get&keyfrom=wap`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|用户未登陆|code=0|
|case_02|用户已登陆|code=0|

## 银行四要素验证

### banks_verify

`api/banks/verify.json`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|正常数据|code=0|
|case_02|银行卡号和类型不是同一个银行|code=1 errors="请选择正确的银行卡类型"|
|case_03|银行卡号少输一位|code=1 errors="银行卡卡号输入有误"|
|case_04|银行卡号为空|code=1 errors="银行卡或者手机号输入有误"|
|测试环境去除了预留手机号和验证码的认证||||
|case_05|预留手机号不对应|code=0 校验数据库测试数据和接口返回的bank_no，phone，bank_name|
|case_06|手机号不完整|code=1 errors="银行卡四要素不正确|
|case_07|银行名称和卡不是同一个银行|code=1 errors="请选择正确的银行卡类型"|
|case_08|手机号为空|code=1 errors="银行卡卡号输入有误"|
|case_09|卡号错误|code=1 errors="银行卡卡号输入有误"|
|case_10|卡类型为空|code=1 errors“请选择开户银行”|
|case_11|卡类型，卡号，收假后全都不对应|code=1 errors="请选择正确的银行卡类型"|

### bank_json 建立在banks_verify的成功的基础上

`api/banks_json`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_12|正向用例|code=0|
|case_13|手机号不是预留手机号|预期不过 实际 code=0 校验数据bank_no bank_phone bank_name|
|case_14|手机号为12345678910|code=0 errors="预留手机号错误,请重新绑卡"|

## 上传联系人

### contact

`api/authentications/contact.json`

备注：接口层面并没有做很严格的限制，重复的限制基本都做在前端

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|正常数据|code=0 校验接口返回的数据和测试数据 name relation phone|
|case_02|联系人姓名为空|code=1 errors=“联系人姓名x输入有误”|
|case_03|联系人信息为字母|code=1 errors=“联系人姓名x输入有误”|
|case_04|联系人姓名为一个空格|code=1 errors=“联系人姓名x输入有误”|
|case_05|两个联系人的关系都是父母 电话不同|code=1 校验接口返回的数据和测试数据 name relation phone|
|case_06|两个联系人的关系都是父母 电话相同||
|case_07|两个联系人都是父母 电话名字相同||
|case_08|两个联系人关系都是配偶||
|case_09|第一个联系人的关系为空||
|case_10|电话是用户的电话|code=1 errors="联系人手机号不能与本人手机号相同"|
|case_11|电话为空|code=1 errors="请输入正确的手机长号"|
|case_12|电话号码为字母|code=1 errors="请输入正确的手机长号"|
|case_13|电话号码少写了一位|code=1 errors="请输入正确的手机长号"|
|case_14|电话号码是座机号码|code=1 errors="请输入正确的手机长号"|
|case_15|错误的关系||

## 用户信息

### info 

`api/accounts/info.json?method=get&keyfrom=wap`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|未登陆情况下|code=1000 errors="用户未登陆"|
|case_02|登陆情况下|code=0 校验接口返回的phone和login|

## 获取贷款信息

### loans

`api/instalments/loans.json?method=get&keyfrom=wap`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|未登陆情况下|code=1000 errors=“用户未登陆”|
|case_02|刚注册完成，没有贷款的情况下|code=0|
|case_03|贷款2000|code=0 和balance=2000|

### 获取还款信息

### repay_info

`api/instalments/repay_info.json?method=get&keyfrom=wap`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|用户未登陆|code=1000 errors=“用户未登陆”|
|case_02|刚注册的用户|code=1 errors="您还没有分期！"|
|case_03|贷款2000|code=0 balabnce=2000|

## 计算还款金额

### fee

`api/instalments/fee?balance="+balance+"&term="+term+"&keyfrom=wap`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|正常用例借款金额为1000 期数term=3|code=0|
|case_02|借款金额为负数-1||
|case_03|借款金额为0||
|case_04|借款金额为10||
|case_05|借款金额为99||
|case_06|正常用例借款金额为1000 期数term=6||
|case_07|借款金额为1001||
|case_08|借款金额为1100||
|case_09|借款金额为4999||
|case_10|借款金额为5000||
|case_11|借款金额为5001||
|case_12|借款金额为200000||
|case_13|借款金额为5000 期数term=6||
|case_14|借款期数term=0||
|case_15|借款期数term=-1||
|case_16|借款期数term=4||
|case_17|借款期数term=100||

总结：该计算机接口基本没做限制，借款lend接口会对这些参数进行限制，这里的用例可以适当删减

## 获取借款人基本信息

### get instalment

`api/authentications/instalment.json?method=get&keyfrom=wap`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|未注册用户|code=1000 errors=“用户未登陆”|
|case_02|新注册用户|code=0 relation=null|
|case_03|提交过基本信息|code=0

### post instalment

`api/authentications/instalment.json`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_04|首次提交|code=0 msg="信息保存成功"|
|case_05|公司为空||
|case_06|家庭住址为空||
|case_07|职业为空||
|case_08|收入为空||
|case_09|错误地址||
|case_10|修改信息|code=0 信息保存成功|
|case_11|借款期间修改信息|code=1 errors="对不起,借款期间不能修改个人信息!"|

## 借款

### lend

`api/instalments/lend`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|正常用例|code=0 校验接口返回数据balance term|
|case_02|借款用途为空|预期不给过，实际给过|
|case_03|借款金额为-1|code=1 errors="借款金额有误，请重新退出填写"|
|case_04|借款金额为0|code=1 errors="借款金额有误，请重新退出填写"|
|case_05|借款金额为10|code=1 errors="借款金额有误，请重新退出填写"|
|case_06|借款金额为99|code=1 errors="借款金额有误，请重新退出填写"|
|case_07|借款金额为1001|预期不能过，实际可以，借款金额整数只是前端做了限制
|case_08|借款金额为1100|code=0 校验接口返回数据balance term|
|case_09|借款金额为4900|code=0 校验接口返回数据balance term|
|case_10|借款金额为5000|code=0 校验接口返回数据balance term|
|case_11|借款金额为5001|code=0 校验接口返回数据balance term|
|case_12|借款金额为200000|预期不能过，但是这里没有限制，1000-5000的限制设置在打款的接口|
|case_13|借款今晚为5000，期数为6期|code=0 校验接口返回数据balance term|
|case_14|期数为0|code=1 errors=“借款期限有误，请重新退出填写”|
|case_15|期数为-1|code=1 errors=“借款期限有误，请重新退出填写”|
|case_16|期数为4|errors=“借款期限有误，请重新退出填写”|
|case_17|期数为100|errors=“借款期限有误，请重新退出填写”|
|case_18|期数为空|errors=“借款期限有误，请重新退出填写”|
|case_19|借款金额为空|code=1 errors="借款金额有误，请重新退出填写"|
|case_20|借款期数为12期|code=0 校验接口返回数据balance term|
|case_21|有待还款的接口==借款时，再次借款|code=1 errors="您已经有一笔贷款未还或正在审核"|

## 还款

### api/instalments/pre_repay

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|还款第一单|code=0|

### instalments/repay

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_02|支付宝还款|code=0|
|case_03|银行卡付款|code=0|

## 贷超

## ecommerc

`http://test.bluecard1000.com:8888/ecommerce/products.json?catalog_id=1&keyfrom=wap`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|catalog_id=1|热门推荐|code=0|
|catalog_id=2|数码产品|code=0|

## 发送验证码

`api/accounts/generate_phone_code.json?phone=" + phone + "&type=bind_phone`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|正常手机号|testNG自校验|
|case_01|空手机号|预期不能通过，实际接口返回原则通过|

总结：发送验证码接口对输入参数没有设计的很严谨～

## 注册登陆

### is_signup

`api/accounts/is_signup`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|用户未注册|code=0 is_signup=false|
|case_02|用户已经注册|code=0 is_signup=true|

### login

`api/accounts/login.json`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_03|未注册用户/错误用户名和密码|code=1 error=“用户名或密码错误!”|
|case_04|正常用户|code=0 校验接口返货的login|

### sign_up

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_05|正常注册|code=0|
|case_06|已经注册过的用户注册|code=1 error=“您已经注册过，请直接登陆”|

### 手机验证码登陆 phone_login

`api/accounts/phone_login.json`

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|正常用例|code=0|
|case_02|输入空验证码|code=1 error=“无效的验证码”|
|case_03|错误验证码|code=1 error="无效的验证码"|

## 借款流程用例

### lend_pro

|用例编号|操作|校验|
|:------|:-----:|-----:|
|case_01|注册到身份认证完成||
|case_02|注册到贷款申请成功||
|case_03|注册到联系人上传完成||
|case_04|注册到淘宝认证完成||
|case_05|注册到借款审核成功并打款||
|case_06|注册到借款预期||
|case_07|注册到登陆||
|case_08|注册到联系人上传完成||
|case_09|注册到身份证上传成功||
|case_10|注册到身份证认证成功||