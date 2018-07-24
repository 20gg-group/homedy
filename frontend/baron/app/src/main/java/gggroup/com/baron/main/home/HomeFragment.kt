package gggroup.com.baron.main.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator
import gggroup.com.baron.R
import gggroup.com.baron.adapter.ViewPagerAdapter
import gggroup.com.baron.entities.Post
import gggroup.com.baron.utils.OnPagerNumberChangeListener

class HomeFragment : Fragment(), OnPagerNumberChangeListener {

    private var pagerAdapter: ViewPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, null)

        val posts = exampleData()

        pagerAdapter = ViewPagerAdapter(context, posts)

        val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        val pagerIndicator = view.findViewById<IndefinitePagerIndicator>(R.id.viewpager_pager_indicator)

        viewPager.adapter = pagerAdapter
        pagerIndicator.attachToViewPager(viewPager)

        return view
    }

    override fun onPagerNumberChanged() {
        pagerAdapter?.notifyDataSetChanged()
    }

    private fun exampleData() : MutableList<Post> =
            mutableListOf(
                    Post("Phòng full nội thất, giờ tự do, có thang máy, giường tủ máy lạnh tivi, mới xây ( cv làng hoa )",
                            "Đường Phạm Văn Chiêu, Quận Gò Vấp, Hồ Chí Minh",
                            "3 Triệu/tháng",
                            "25m²",
                            "0933310310",
                            "Phòng cao cấp giá rẻ tại Gò Vấp 3tr tháng\n" +
                                    "Tiện nghi đầy đủ\n" +
                                    "➡ máy lạnh - dường nệm- tivi 32 in\n" +
                                    "➡ tủ quần áo, bàn ghế tiếp khách, tủ lạnh\n" +
                                    "➡ vệ sinh cao cấp vòi hoa sen, nước nóng lạnh\n" +
                                    "➡ đi lại bằng thang máy\n" +
                                    "➡ tất cả được trang bị mới 100%\n" +
                                    "➡ giờ giấc tự do , có lối thoát hiểm\n" +
                                    "➡ gần cv làng hoa, có máy giặt\n" +
                                    "15p ra sân bay ... gần chợ\n" +
                                    "⛳ Có bảo vệ 24/24 camera an ninh tuyệt đối\n" +
                                    "chỉ cần mang đồ đến ở nội thất đã được sắm mới\n" +
                                    "hình thật bên dưới\n" +
                                    "⚠ giá : từ 3tr - 3,5tr/ tháng\n" +
                                    "111/42 Phạm Văn Chiêu, P.14, Gò Vấp\n" +
                                    "Lh: 0933 310 310 - 01667.158.158 ( mr.trí )",
                            "https://static.phongtro123.com/uploads/2018/07/E08989A5-3A88-42A2-9F25-5BCFEDB94ABD.jpeg"
                    ),
                    Post("Căn hộ mini full nội thất, có bếp, tivi, tủ lạnh máy giặt, máy lạnh giờ tự do. Sát sân bay công viên gia định",
                            "Đường Bạch Đằng, Phường 2, Quận Tân Bình, Hồ Chí Minh",
                            "6 triệu/tháng",
                            "35m²",
                            "0966865255",
                            "Phòng mới xây giá rẻ dạng căn hộ minh ni. Đủ nội thất cao cấp. Ngay tại sân bay chỉ 5p ra công viên gia định và 5p ra sân bay\n" +
                                    "- Thích hợp cặp vợ chồng, nhân viên văn phòng, ở và buôn bán online\n" +
                                    "- thiết kế hiện đại mỗi phòng có thiết kế khác nhau\n" +
                                    "Khu nhà biệt thự . Toàn tiếp viên ở , tiện ích xung quanh . Rạp phim . Công viên, siêu thị parkson ,...phòng gym....yoga\n" +
                                    "Tiện nghi đầy đủ\n" +
                                    "➡ máy lạnh - dường nệm- tivi 32 in\n" +
                                    "➡ tủ quần áo, bàn ghế tiếp khách, tủ lạnh\n" +
                                    "➡ vệ sinh cao cấp vòi hoa sen, nước nóng lạnh\n" +
                                    "Bồn tắm đứng cao cấp\n" +
                                    "➡ phòng có bancon thoáng mát, có máy giặt\n" +
                                    "➡ tất cả được trang bị mới\n" +
                                    "➡ giờ giấc tự do , an ninh camera 24/24\n" +
                                    "➡ gần cv gia định, sát sân bay, khu toàn tiếp viên và phi công ở . Chìa khoá riêng\n" +
                                    "⛳ an ninh tuyệt đối , có bếp nấu ăn từng phòng . Tiện ích của một căn hộ mini ngay tại sân bay\n" +
                                    "chỉ cần mang đồ đến ở nội thất đã được sắm mới\n" +
                                    "hình thật bên dưới\n" +
                                    "⚠ giá : 5tr-6tr có thể ở được 4 người / phòng\n" +
                                    "54/30/15 bạch đằng . P2 tân bình\n" +
                                    "Hẻm xe hơi vào tận nhà\n" +
                                    "- đặc biệt nhà chỉ có 6 phòng nên lượng xe rất ít nhà xe rộng rãi , khoá vân tay, camera an ninh tuyệt đối\n" +
                                    "- Đối diện trường đào tạo phi công đường hồng Hà . Có hai lối ra rất thuận tiện .\n" +
                                    "Gần sát vinmart , highland coffee, chợ tân sơn nhất , sáng sớm có thể đi bộ ra công viên tập thể dục )\n" +
                                    "Lh: 0966.865255 - 0888.555.858 (mr. Hoàn chính chủ cho thuê )",
                            "https://static.phongtro123.com/uploads/2018/06/03237711-BAFE-461B-A5D0-DD5A040F308A-1024x768.jpeg"
                    ),
                    Post("CHO THUÊ MẶT BẰNG, NHÀ NGUYÊN CĂN, VĂN PHÒNG KINH DOANH, KHU VỰC QUẬN 9",
                            "Đường 339, Phường Phước Long B, Quận 9, Hồ Chí Minh",
                            "16 triệu/tháng",
                            "100m²",
                            "0903099258",
                            "# 1- chính chủ cho thuê văn phòng kinh doanh đường 297 diện tích sử dụng 90 m2 (giá 3,5 triệu ) (không ở lại )\n" +
                                    "# 2- chính chủ cho thuê căn nhà mặt tiền đường ngô quyền q9 diện tích 85m2 , 1 trệt 1 lầu, 3 phòng ngủ ( giá 9,5 triệu)\n" +
                                    "# 3 - chính chủ cho thuê nhà mặt tiền đỗ xuân hợp 1 trệt 3 lầu, 4 phòng ngủ diện tích 90m2 ( giá 21 triệu )\n" +
                                    "# 4 - cho thuê nhà mặt tiền đường 339 , 1 trệt 2 lầu, 4 phòng ngủ diện tích 100m2 ( giá 16 triệu )\n" +
                                    "tấc cả nhà đều nằm ngay trung tâm, các trường cao đẳng công thương , đại học văn hóa, cao đẳng kỹ nghệ, gần khu nhà ở nam hòa sầm uất, nơi hội tụ những cty bất động sản, dịch vụ ăn uống 24h, lotte, con cung....\n" +
                                    "tấc cả nhà trên đều nằm ở những con đường huyết mạch, dân cư đông đúc phù hợp kinh doanh tấc cả các nghành nghề,\n" +
                                    "rất vui lòng được hợp tác cùng các bạn\n" +
                                    "liên hệ gặp anh phước : 0903.099.258\n" +
                                    "chuyên cho thuê mặt bằng kinh doanh , nhà nguyên căn , văn phòng kinh doanh , phòng trọ , căn hộ khu vực quận 9 tấc cả các phường nằm trong khu vực quận 9 thành phố hồ chí minh .",
                            "https://static.phongtro123.com/uploads/2018/07/ffc028bece9d2fc3768c-768x1024.jpg"
                    ),
                    Post("CHO THUÊ CĂN HỘ CAO CẤP ĐẦY ĐỦ TIỆN NGHI FULL NỘI THẤT NGAY NGÃ TƯ PHAN ĐĂNG LƯU. PHÒNG SẠCH SẼ MỚI 100%",
                            "Nguyễn Trọng Tuyển, 8, Quận Phú Nhuận, Hồ Chí Minh",
                            "4.8 triệu/tháng",
                            "25m²",
                            "0937141292",
                            "Phòng full nội thất ngay Nguyễn Trọng Tuyển\n" +
                                    "Nhà mới sơn sửa, phòng rộng rãi, thoáng mát, ngay trung tâm quận Phú Nhuận\n" +
                                    "Phòng rộng 18m2 - 25m2, sạch sẽ, được trang bị tiện nghi:\n" +
                                    "- Nệm, tủ đồ.\n" +
                                    "- Máy lạnh, mới 100%.\n" +
                                    "- Máy giặt.\n" +
                                    "- WC riêng.\n" +
                                    "Phòng mới, đẹp, sạch sẽ, đường rộng rãi, đi xe buýt rất thuận tiện, gần chợ, siêu thị, cafe, khu ăn uống Phan Xích Long sầm uất.\n" +
                                    "+ Chỗ để xe rộng, camera 24/24.\n" +
                                    "+ Dọn vệ sinh hàng tuần.\n" +
                                    "Giá thuê: 4.8tr/ tháng\n" +
                                    "Điện: 3500đ/KWH, nước: 100.000đ/người, internet: 100.000đ/phòng\n" +
                                    "Địa chỉ: 207/16 Nguyễn Trọng Tuyển, P8. Phú Nhuận",
                            "https://static.phongtro123.com/uploads/2018/06/3816BB3E-5F2F-491B-944D-8EE36201CD50.jpeg"
                    ),
                    Post("CĂN HỘ TIỆN ÍCH ROYAL APARTMENT NGAY THÀNH THÁI, TÔ HIẾN THÀNH, QUẬN 10",
                            "163/26 Đường Thành Thái, Phường 14, Quận 10, Hồ Chí Minh",
                            "7 triệu/tháng",
                            "25m²",
                            "0938534042",
                            "CĂN HỘ TIỆN ÍCH ROYAL APARTMENT NGAY THÀNH THÁI, TRUNG TÂM QUẬN 10\n" +
                                    "---ĐỊA CHỈ: 163/26 Thành Thái, P.14, Q.10, TP.HCM ( ngay Tô Hiến Thành và Thành Thái).\n" +
                                    "---TIỆN ÍCH: Căn hộ nằm ngay trên trục Thành Thái, Tô Hiến Thành, Sư Vạn Hạnh; Ngay trung tâm q.10, gần các trung tâm thương mại lớn, dịch vụ tiện ích, siêu thị Big C, Bệnh viện, công viên Lê Thị Riêng và các địa điểm ăn uống, gần các trường ĐH ngoại ngữ tin học, ĐH bách khoa, ĐH Y khoa Phạm Ngọc Thạch, ĐH kinh tế... Khu vực hẻm ô tô yên tĩnh, an ninh, tri thức, thuận tiện qua lại trung tâm các quận 1, 3, 5, 10, Phú Nhuận,…\n" +
                                    "- Căn hộ được thiết kế sang trọng, hiện đại, sạch sẽ, thoáng mát rộng từ 20-30m2, trang thiết bị nội thất mới 100% như: Máy lạnh, tivi, tủ lạnh, bếp điện từ, giường nệm, tủ quần áo, bộ bàn ăn, máy nước nóng lạnh…\n" +
                                    "+ Ban công, cửa sổ lớn đón gió, thoáng mát.\n" +
                                    "+ WC rộng rãi, sạch sẽ.\n" +
                                    "+ Kệ bếp nấu ăn sang trọng riêng biệt.\n" +
                                    "+ Hệ thống cửa từ bảo mật cho từng phòng.\n" +
                                    "+ Wifi tốc độ cao, điện nước ổn định.\n" +
                                    "+ Hệ thống thang máy tiện ích.\n" +
                                    "+ Máy giặt sử dụng FREE, sân phơi đồ rộng rãi thông thoáng.\n" +
                                    "+ Chỗ để xe ngay trong nhà, camera giám sát an ninh, bảo vệ 24/24, dịch vụ dọn vệ sinh hàng tuần (3 lần/tuần).\n" +
                                    "---GIÁ THUÊ: chỉ từ 7.0tr – 9.5tr/tháng\n" +
                                    "---LIÊN HỆ CHÍNH CHỦ: A. Tiến: 0938534042 (để hẹn lịch xem phòng).\n" +
                                    "Xin cảm ơn!",
                            "https://static.phongtro123.com/uploads/2018/03/20180204_182256-1024x768.jpg"))
}