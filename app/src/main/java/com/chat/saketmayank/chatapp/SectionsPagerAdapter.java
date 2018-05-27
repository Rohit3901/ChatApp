package com.chat.saketmayank.chatapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chat.saketmayank.chatapp.Fragments.ChatsFragment;
import com.chat.saketmayank.chatapp.Fragments.FriendsFragment;
import com.chat.saketmayank.chatapp.Fragments.RequestsFragment;


class SectionsPagerAdapter extends FragmentPagerAdapter{


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
//            case 0:
//                RequestsFragment requestsFragment = new RequestsFragment();
//                return requestsFragment;

            case 0:
                ChatsFragment chatsFragment = new ChatsFragment();
                return  chatsFragment;

            case 1:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

            default:
                return  null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){

        switch (position) {
//            case 0:
//                return "REQUESTS";

            case 0:
                return "CHATS";

            case 1:
                return "FRIENDS";

            default:
                return null;
        }

    }

}
