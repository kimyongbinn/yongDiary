//package com.example.yongDiary.configuration.filter;
//
//
//public enum Role {
//    USER(1),      // 일반 사용자
//    ADMIN(2);     // 관리자
//
//    private final int memAdmin;  // 각 역할에 대응하는 숫자 값
//
//    Role(int memAdmin) {
//        this.memAdmin = memAdmin;
//    }
//
//    public int getAdmin() {
//        return memAdmin;
//    }
//    
//
//    public static Role getRoleByAdmin(int memAdmin) {
//        for (Role role : Role.values()) {
//            if (role.getAdmin() == memAdmin) {
//                return role;
//            }
//        }
//        throw new IllegalArgumentException("Invalid category: " + memAdmin);
//    }
//}
