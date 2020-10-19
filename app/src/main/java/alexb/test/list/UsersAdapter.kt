package alexb.test.list

import alexb.test.R
import alexb.test.model.Post
import alexb.test.model.Users
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Button
import android.widget.TextView


class UsersAdapter(private val context: Context,private val users:List<Pair<Users, List<Post>>> ):BaseExpandableListAdapter() {
    override fun getGroup(groupPosition: Int): Users {
        return users[groupPosition].first
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val group = getGroup(groupPosition)
        var view = convertView
        if (view == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.user_item, parent,false)
        }
        val name = view?.findViewById(R.id.name_user) as TextView
        val mail = view.findViewById(R.id.mail) as TextView
        val phone = view.findViewById(R.id.phone) as TextView
        name.text =  group.name
        mail.text = group.email
        phone.text = group.phone
        val website = view.findViewById(R.id.website) as Button
        website.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://"+users[groupPosition].first.website)
            website.context.startActivity(intent)
        }
        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return users[groupPosition].second.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Post {
        return users[groupPosition].second[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val child = getChild(groupPosition,childPosition)
        var view: View? = convertView
        if (view == null) {
            val layoutInflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = layoutInflater.inflate(R.layout.post_item, null)
        }
        val title = view?.findViewById(R.id.title_post) as TextView
        val body = view.findViewById(R.id.post) as TextView
        title.text = child.title
        body.text = child.body
        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return users.size
    }
}