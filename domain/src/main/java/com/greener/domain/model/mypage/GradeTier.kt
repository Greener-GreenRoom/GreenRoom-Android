package com.greener.domain.model.mypage

enum class GradeTier(val tierName:String,val tierBegin:Int, val tierEnd:Int,val tierCode:String) {
    NONE("",0,0,"none"),
    ROOT("뿌리집사",1,5,"root"),
    SPROUT("새싹집사",6,10,"sprout"),
    STEM("줄기집사",11,15,"stem"),
    BRANCH("가지집사",16,20,"branch"),
    LEAVES("잎집사",21,25,"leaves"),
    BERRY("열매집사",26,30,"berry"),
    TREE("나무집사",31,35,"tree");



}