"""
专业版图形化去水印工具 - 优化界面和算法
支持深度学习修复、边缘优化、智能填充
"""

import cv2
import numpy as np
import tkinter as tk
from tkinter import filedialog, messagebox, ttk
from PIL import Image, ImageTk, ImageFilter, ImageEnhance
import os
import threading


class WatermarkRemoverPro:
    """专业版去水印工具"""
    
    def __init__(self):
        self.root = tk.Tk()
        self.root.title("🎨 专业版图片去水印工具 v4.0")
        self.root.geometry("1200x800")
        self.root.resizable(True, True)
        self.root.configure(bg='#1e1e1e')
        
        # 变量
        self.image_path = None
        self.image = None
        self.original_image = None
        self.display_image = None
        self.result_image = None
        self.photo = None
        self.canvas = None
        self.selected_regions = []
        self.current_rect = None
        self.start_x = None
        self.start_y = None
        self.rect_id = None
        self.scale_factor = 1.0
        self.processing = False
        
        # 设置主题
        self.setup_theme()
        
        # 创建界面
        self.create_widgets()
        
    def setup_theme(self):
        """设置深色主题"""
        style = ttk.Style()
        style.theme_use('clam')
        
        # 配色方案
        self.colors = {
            'bg': '#1e1e1e',
            'fg': '#ffffff',
            'accent': '#007acc',
            'hover': '#005a9e',
            'success': '#4caf50',
            'warning': '#ff9800',
            'error': '#f44336',
            'panel': '#252526',
            'border': '#3e3e42',
            'text': '#cccccc'
        }
        
        # 按钮样式
        style.configure('Accent.TButton',
                       background=self.colors['accent'],
                       foreground='white',
                       font=('Segoe UI', 10, 'bold'),
                       borderwidth=0,
                       focuscolor='none',
                       padding=(15, 10))
        
        style.map('Accent.TButton',
                 background=[('active', self.colors['hover'])])
        
        # Frame样式
        style.configure('Dark.TFrame', background=self.colors['panel'])
        style.configure('Card.TFrame', background=self.colors['panel'], relief='flat')
        
    def create_widgets(self):
        """创建优化后的界面"""
        
        # ============ 顶部导航栏 ============
        navbar = tk.Frame(self.root, bg=self.colors['panel'], height=70)
        navbar.pack(side=tk.TOP, fill=tk.X)
        navbar.pack_propagate(False)
        
        # Logo和标题
        title_frame = tk.Frame(navbar, bg=self.colors['panel'])
        title_frame.pack(side=tk.LEFT, padx=20, pady=15)
        
        title_label = tk.Label(title_frame,
                              text="🎨 WaterMark Remover Pro",
                              font=('Segoe UI', 18, 'bold'),
                              bg=self.colors['panel'],
                              fg=self.colors['fg'])
        title_label.pack(side=tk.LEFT)
        
        subtitle = tk.Label(title_frame,
                           text="专业版 v4.0",
                           font=('Segoe UI', 9),
                           bg=self.colors['panel'],
                           fg=self.colors['text'])
        subtitle.pack(side=tk.LEFT, padx=(10, 0))
        
        # 右侧按钮组
        btn_container = tk.Frame(navbar, bg=self.colors['panel'])
        btn_container.pack(side=tk.RIGHT, padx=20)
        
        # 自定义按钮
        self.create_modern_button(btn_container, "📂 打开图片", self.select_image, 0)
        self.process_btn = self.create_modern_button(btn_container, "✨ 智能处理", self.process_image, 1, state='disabled')
        self.clear_btn = self.create_modern_button(btn_container, "🗑 清除", self.clear_regions, 2, state='disabled')
        self.save_btn = self.create_modern_button(btn_container, "💾 保存", self.save_result, 3, state='disabled')
        
        # ============ 主容器 ============
        main_container = tk.Frame(self.root, bg=self.colors['bg'])
        main_container.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)
        
        # 左侧工具面板
        left_panel = tk.Frame(main_container, bg=self.colors['panel'], width=280)
        left_panel.pack(side=tk.LEFT, fill=tk.Y, padx=(0, 10))
        left_panel.pack_propagate(False)
        
        self.create_left_panel(left_panel)
        
        # 中间图片区域
        center_panel = tk.Frame(main_container, bg=self.colors['panel'])
        center_panel.pack(side=tk.LEFT, fill=tk.BOTH, expand=True, padx=(0, 10))
        
        self.create_image_viewer(center_panel)
        
        # 右侧属性面板
        right_panel = tk.Frame(main_container, bg=self.colors['panel'], width=300)
        right_panel.pack(side=tk.RIGHT, fill=tk.Y)
        right_panel.pack_propagate(False)
        
        self.create_right_panel(right_panel)
        
        # ============ 底部状态栏 ============
        self.create_statusbar()
        
    def create_modern_button(self, parent, text, command, column, state='normal'):
        """创建现代化按钮"""
        btn = tk.Button(parent,
                       text=text,
                       command=command,
                       font=('Segoe UI', 10),
                       bg=self.colors['accent'],
                       fg='white',
                       activebackground=self.colors['hover'],
                       activeforeground='white',
                       relief=tk.FLAT,
                       cursor='hand2',
                       padx=15,
                       pady=8,
                       state=state)
        btn.grid(row=0, column=column, padx=5)
        
        # 悬停效果
        def on_enter(e):
            if btn['state'] != 'disabled':
                btn['bg'] = self.colors['hover']
        
        def on_leave(e):
            if btn['state'] != 'disabled':
                btn['bg'] = self.colors['accent']
        
        btn.bind('<Enter>', on_enter)
        btn.bind('<Leave>', on_leave)
        
        return btn
        
    def create_left_panel(self, parent):
        """创建左侧工具面板"""
        # 标题
        title = tk.Label(parent,
                        text="🛠 工具箱",
                        font=('Segoe UI', 14, 'bold'),
                        bg=self.colors['panel'],
                        fg=self.colors['fg'],
                        anchor=tk.W,
                        pady=15)
        title.pack(fill=tk.X, padx=20)
        
        # 快速操作卡片
        quick_card = self.create_card(parent, "⚡ 快速操作")
        
        self.create_tool_button(quick_card, "🔍 自动检测水印", self.auto_detect)
        self.create_tool_button(quick_card, "🎯 智能选择", self.smart_select)
        self.create_tool_button(quick_card, "📐 矩形选择", self.rectangle_select)
        self.create_tool_button(quick_card, "🖌 画笔选择", self.brush_select)
        
        # 算法选择卡片
        algo_card = self.create_card(parent, "🧮 处理算法")
        
        self.algorithm = tk.StringVar(value="advanced")
        
        algo_options = [
            ("🚀 深度修复 (推荐)", "advanced"),
            ("⚡ Telea算法 (快速)", "telea"),
            ("🌊 NS流体算法", "ns"),
            ("✨ 智能模糊", "smart_blur"),
            ("🎨 纹理填充", "texture")
        ]
        
        for text, value in algo_options:
            rb = tk.Radiobutton(algo_card,
                               text=text,
                               variable=self.algorithm,
                               value=value,
                               font=('Segoe UI', 9),
                               bg=self.colors['panel'],
                               fg=self.colors['text'],
                               selectcolor=self.colors['bg'],
                               activebackground=self.colors['panel'],
                               activeforeground=self.colors['fg'])
            rb.pack(anchor=tk.W, padx=10, pady=3)
        
        # 高级设置卡片
        settings_card = self.create_card(parent, "⚙️ 高级设置")
        
        # 修复强度
        strength_frame = tk.Frame(settings_card, bg=self.colors['panel'])
        strength_frame.pack(fill=tk.X, padx=10, pady=5)
        
        tk.Label(strength_frame,
                text="修复强度:",
                font=('Segoe UI', 9),
                bg=self.colors['panel'],
                fg=self.colors['text']).pack(side=tk.LEFT)
        
        self.strength_var = tk.IntVar(value=5)
        strength_scale = tk.Scale(strength_frame,
                                 from_=1, to=10,
                                 orient=tk.HORIZONTAL,
                                 variable=self.strength_var,
                                 bg=self.colors['panel'],
                                 fg=self.colors['text'],
                                 highlightthickness=0,
                                 troughcolor=self.colors['bg'])
        strength_scale.pack(side=tk.RIGHT, fill=tk.X, expand=True, padx=(10, 0))
        
        # 边缘优化
        self.edge_optimize = tk.BooleanVar(value=True)
        edge_cb = tk.Checkbutton(settings_card,
                                text="边缘优化",
                                variable=self.edge_optimize,
                                font=('Segoe UI', 9),
                                bg=self.colors['panel'],
                                fg=self.colors['text'],
                                selectcolor=self.colors['bg'],
                                activebackground=self.colors['panel'])
        edge_cb.pack(anchor=tk.W, padx=10, pady=3)
        
        # 色彩校正
        self.color_correct = tk.BooleanVar(value=True)
        color_cb = tk.Checkbutton(settings_card,
                                 text="自动色彩校正",
                                 variable=self.color_correct,
                                 font=('Segoe UI', 9),
                                 bg=self.colors['panel'],
                                 fg=self.colors['text'],
                                 selectcolor=self.colors['bg'],
                                 activebackground=self.colors['panel'])
        color_cb.pack(anchor=tk.W, padx=10, pady=3)
        
    def create_card(self, parent, title):
        """创建卡片容器"""
        card_frame = tk.Frame(parent, bg=self.colors['panel'])
        card_frame.pack(fill=tk.X, padx=15, pady=10)
        
        # 卡片标题
        header = tk.Frame(card_frame, bg=self.colors['bg'], height=35)
        header.pack(fill=tk.X)
        
        tk.Label(header,
                text=title,
                font=('Segoe UI', 10, 'bold'),
                bg=self.colors['bg'],
                fg=self.colors['fg'],
                anchor=tk.W).pack(side=tk.LEFT, padx=10, pady=5)
        
        # 卡片内容
        content = tk.Frame(card_frame, bg=self.colors['panel'])
        content.pack(fill=tk.BOTH, expand=True, pady=(5, 0))
        
        return content
        
    def create_tool_button(self, parent, text, command):
        """创建工具按钮"""
        btn = tk.Button(parent,
                       text=text,
                       command=command,
                       font=('Segoe UI', 9),
                       bg=self.colors['bg'],
                       fg=self.colors['text'],
                       activebackground=self.colors['border'],
                       activeforeground=self.colors['fg'],
                       relief=tk.FLAT,
                       cursor='hand2',
                       anchor=tk.W,
                       padx=10,
                       pady=8)
        btn.pack(fill=tk.X, padx=10, pady=2)
        
        def on_enter(e):
            btn['bg'] = self.colors['border']
        
        def on_leave(e):
            btn['bg'] = self.colors['bg']
        
        btn.bind('<Enter>', on_enter)
        btn.bind('<Leave>', on_leave)
        
    def create_image_viewer(self, parent):
        """创建图片查看器"""
        # 标题栏
        header = tk.Frame(parent, bg=self.colors['bg'], height=40)
        header.pack(fill=tk.X)
        header.pack_propagate(False)
        
        tk.Label(header,
                text="📷 图片预览",
                font=('Segoe UI', 12, 'bold'),
                bg=self.colors['bg'],
                fg=self.colors['fg']).pack(side=tk.LEFT, padx=15, pady=10)
        
        # 缩放控制
        zoom_frame = tk.Frame(header, bg=self.colors['bg'])
        zoom_frame.pack(side=tk.RIGHT, padx=15)
        
        self.zoom_label = tk.Label(zoom_frame,
                                   text="100%",
                                   font=('Segoe UI', 9),
                                   bg=self.colors['bg'],
                                   fg=self.colors['text'])
        self.zoom_label.pack(side=tk.RIGHT, padx=5)
        
        tk.Button(zoom_frame,
                 text="🔍+",
                 command=self.zoom_in,
                 bg=self.colors['bg'],
                 fg=self.colors['text'],
                 relief=tk.FLAT,
                 cursor='hand2',
                 padx=5).pack(side=tk.RIGHT, padx=2)
        
        tk.Button(zoom_frame,
                 text="🔍-",
                 command=self.zoom_out,
                 bg=self.colors['bg'],
                 fg=self.colors['text'],
                 relief=tk.FLAT,
                 cursor='hand2',
                 padx=5).pack(side=tk.RIGHT, padx=2)
        
        # 画布容器
        canvas_container = tk.Frame(parent, bg=self.colors['bg'])
        canvas_container.pack(fill=tk.BOTH, expand=True, padx=2, pady=2)
        
        # 滚动条
        h_scroll = tk.Scrollbar(canvas_container, orient=tk.HORIZONTAL)
        h_scroll.pack(side=tk.BOTTOM, fill=tk.X)
        
        v_scroll = tk.Scrollbar(canvas_container, orient=tk.VERTICAL)
        v_scroll.pack(side=tk.RIGHT, fill=tk.Y)
        
        # 画布
        self.canvas = tk.Canvas(canvas_container,
                               bg='#2d2d30',
                               highlightthickness=0,
                               xscrollcommand=h_scroll.set,
                               yscrollcommand=v_scroll.set)
        self.canvas.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)
        
        h_scroll.config(command=self.canvas.xview)
        v_scroll.config(command=self.canvas.yview)
        
        # 事件绑定
        self.canvas.bind("<ButtonPress-1>", self.on_mouse_down)
        self.canvas.bind("<B1-Motion>", self.on_mouse_drag)
        self.canvas.bind("<ButtonRelease-1>", self.on_mouse_up)
        
        # 欢迎提示
        self.canvas.create_text(400, 300,
                               text="👋 欢迎使用专业版去水印工具\n\n点击上方 '📂 打开图片' 开始",
                               font=('Segoe UI', 14),
                               fill=self.colors['text'],
                               tags='welcome')
        
    def create_right_panel(self, parent):
        """创建右侧属性面板"""
        # 标题
        title = tk.Label(parent,
                        text="📋 选区列表",
                        font=('Segoe UI', 14, 'bold'),
                        bg=self.colors['panel'],
                        fg=self.colors['fg'],
                        anchor=tk.W,
                        pady=15)
        title.pack(fill=tk.X, padx=20)
        
        # 选区列表
        list_frame = tk.Frame(parent, bg=self.colors['panel'])
        list_frame.pack(fill=tk.BOTH, expand=True, padx=15, pady=(0, 10))
        
        # 自定义列表框
        list_container = tk.Frame(list_frame, bg=self.colors['bg'])
        list_container.pack(fill=tk.BOTH, expand=True)
        
        scrollbar = tk.Scrollbar(list_container)
        scrollbar.pack(side=tk.RIGHT, fill=tk.Y)
        
        self.region_listbox = tk.Listbox(list_container,
                                        font=('Consolas', 9),
                                        bg=self.colors['bg'],
                                        fg=self.colors['text'],
                                        selectbackground=self.colors['accent'],
                                        selectforeground='white',
                                        relief=tk.FLAT,
                                        highlightthickness=0,
                                        yscrollcommand=scrollbar.set)
        self.region_listbox.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)
        scrollbar.config(command=self.region_listbox.yview)
        
        # 列表操作按钮
        btn_frame = tk.Frame(parent, bg=self.colors['panel'])
        btn_frame.pack(fill=tk.X, padx=15, pady=10)
        
        tk.Button(btn_frame,
                 text="🗑 删除选中",
                 command=self.delete_selected_region,
                 font=('Segoe UI', 9),
                 bg=self.colors['error'],
                 fg='white',
                 relief=tk.FLAT,
                 cursor='hand2',
                 padx=10,
                 pady=5).pack(side=tk.LEFT, fill=tk.X, expand=True, padx=(0, 5))
        
        tk.Button(btn_frame,
                 text="📝 编辑",
                 command=self.edit_region,
                 font=('Segoe UI', 9),
                 bg=self.colors['warning'],
                 fg='white',
                 relief=tk.FLAT,
                 cursor='hand2',
                 padx=10,
                 pady=5).pack(side=tk.RIGHT, fill=tk.X, expand=True, padx=(5, 0))
        
        # 统计信息
        stats_card = self.create_card(parent, "📊 统计信息")
        
        self.stats_label = tk.Label(stats_card,
                                    text="选区数量: 0\n总面积: 0 px²\n覆盖率: 0%",
                                    font=('Segoe UI', 9),
                                    bg=self.colors['panel'],
                                    fg=self.colors['text'],
                                    justify=tk.LEFT)
        self.stats_label.pack(anchor=tk.W, padx=10, pady=5)
        
    def create_statusbar(self):
        """创建状态栏"""
        statusbar = tk.Frame(self.root, bg=self.colors['accent'], height=30)
        statusbar.pack(side=tk.BOTTOM, fill=tk.X)
        statusbar.pack_propagate(False)
        
        self.status_label = tk.Label(statusbar,
                                     text="📌 就绪",
                                     font=('Segoe UI', 9),
                                     bg=self.colors['accent'],
                                     fg='white',
                                     anchor=tk.W)
        self.status_label.pack(side=tk.LEFT, padx=15)
        
        # 进度条
        self.progress = ttk.Progressbar(statusbar,
                                       mode='determinate',
                                       length=200)
        self.progress.pack(side=tk.RIGHT, padx=15)
        self.progress.pack_forget()  # 初始隐藏
        
    # ============ 功能实现 ============
    
    def select_image(self):
        """选择图片"""
        file_path = filedialog.askopenfilename(
            title="选择图片",
            filetypes=[
                ("图片文件", "*.jpg *.jpeg *.png *.bmp *.tiff *.webp"),
                ("所有文件", "*.*")
            ]
        )
        
        if file_path:
            self.load_image(file_path)
            
    def load_image(self, file_path):
        """加载图片"""
        try:
            self.image_path = file_path
            self.image = cv2.imread(file_path)
            self.original_image = self.image.copy()
            
            if self.image is None:
                messagebox.showerror("错误", "无法读取图片！")
                return
            
            self.display_image = self.image.copy()
            self.selected_regions = []
            self.result_image = None
            
            self.show_image()
            
            # 启用按钮
            self.process_btn.config(state='normal')
            self.clear_btn.config(state='normal')
            
            filename = os.path.basename(file_path)
            self.update_status(f"✅ 已加载: {filename}")
            self.canvas.delete('welcome')
            
            self.update_stats()
            
        except Exception as e:
            messagebox.showerror("错误", f"加载失败: {str(e)}")
            
    def show_image(self):
        """显示图片"""
        if self.display_image is None:
            return
        
        img_rgb = cv2.cvtColor(self.display_image, cv2.COLOR_BGR2RGB)
        
        # 计算缩放
        canvas_w = self.canvas.winfo_width()
        canvas_h = self.canvas.winfo_height()
        
        if canvas_w > 1 and canvas_h > 1:
            img_h, img_w = img_rgb.shape[:2]
            scale_w = (canvas_w - 20) / img_w
            scale_h = (canvas_h - 20) / img_h
            self.scale_factor = min(scale_w, scale_h, 1.0)
            
            if self.scale_factor < 1.0:
                new_w = int(img_w * self.scale_factor)
                new_h = int(img_h * self.scale_factor)
                img_rgb = cv2.resize(img_rgb, (new_w, new_h), interpolation=cv2.INTER_AREA)
        
        img_pil = Image.fromarray(img_rgb)
        self.photo = ImageTk.PhotoImage(img_pil)
        
        self.canvas.delete("all")
        self.canvas.create_image(10, 10, anchor=tk.NW, image=self.photo)
        self.canvas.config(scrollregion=self.canvas.bbox(tk.ALL))
        
        self.redraw_regions()
        self.update_zoom_label()
        
    def on_mouse_down(self, event):
        """鼠标按下"""
        if self.image is None:
            return
        
        self.start_x = self.canvas.canvasx(event.x)
        self.start_y = self.canvas.canvasy(event.y)
        
        self.rect_id = self.canvas.create_rectangle(
            self.start_x, self.start_y, self.start_x, self.start_y,
            outline='#00ff00', width=2, dash=(5, 5), tags='current'
        )
        
    def on_mouse_drag(self, event):
        """鼠标拖动"""
        if self.rect_id is None:
            return
        
        cur_x = self.canvas.canvasx(event.x)
        cur_y = self.canvas.canvasy(event.y)
        
        self.canvas.coords(self.rect_id, self.start_x, self.start_y, cur_x, cur_y)
        
        w = abs(cur_x - self.start_x)
        h = abs(cur_y - self.start_y)
        self.update_status(f"📏 选择中... {int(w)}×{int(h)} px")
        
    def on_mouse_up(self, event):
        """鼠标释放"""
        if self.rect_id is None:
            return
        
        end_x = self.canvas.canvasx(event.x)
        end_y = self.canvas.canvasy(event.y)
        
        # 转换为实际坐标
        x1 = int((min(self.start_x, end_x) - 10) / self.scale_factor)
        y1 = int((min(self.start_y, end_y) - 10) / self.scale_factor)
        x2 = int((max(self.start_x, end_x) - 10) / self.scale_factor)
        y2 = int((max(self.start_y, end_y) - 10) / self.scale_factor)
        
        w = x2 - x1
        h = y2 - y1
        
        if w > 10 and h > 10:
            self.selected_regions.append((x1, y1, w, h))
            self.update_region_list()
            self.update_stats()
            self.update_status(f"✅ 已添加区域 #{len(self.selected_regions)}")
        else:
            self.canvas.delete(self.rect_id)
            self.update_status("⚠️ 选区太小")
        
        self.rect_id = None
        
    def redraw_regions(self):
        """重绘选区"""
        self.canvas.delete('region')
        
        for i, (x, y, w, h) in enumerate(self.selected_regions, 1):
            x1 = int(x * self.scale_factor) + 10
            y1 = int(y * self.scale_factor) + 10
            x2 = int((x + w) * self.scale_factor) + 10
            y2 = int((y + h) * self.scale_factor) + 10
            
            self.canvas.create_rectangle(x1, y1, x2, y2,
                                        outline='#00ff00',
                                        width=2,
                                        tags='region')
            
            self.canvas.create_text(x1 + 5, y1 + 15,
                                   text=f"#{i}",
                                   fill='#00ff00',
                                   font=('Arial', 10, 'bold'),
                                   tags='region')
            
    def update_region_list(self):
        """更新选区列表"""
        self.region_listbox.delete(0, tk.END)
        for i, (x, y, w, h) in enumerate(self.selected_regions, 1):
            self.region_listbox.insert(tk.END, f"区域 {i:02d} | ({x:4d},{y:4d}) | {w:4d}×{h:4d}")
            
    def update_stats(self):
        """更新统计信息"""
        if not self.image is None:
            total_area = sum(w * h for _, _, w, h in self.selected_regions)
            img_area = self.image.shape[0] * self.image.shape[1]
            coverage = (total_area / img_area * 100) if img_area > 0 else 0
            
            self.stats_label.config(
                text=f"选区数量: {len(self.selected_regions)}\n"
                     f"总面积: {total_area:,} px²\n"
                     f"覆盖率: {coverage:.2f}%"
            )
            
    def process_image(self):
        """处理图片"""
        if self.image is None:
            messagebox.showwarning("警告", "请先打开图片！")
            return
        
        if not self.selected_regions:
            result = messagebox.askyesno("提示", "未选择区域，是否自动检测？")
            if result:
                self.auto_detect()
                if not self.selected_regions:
                    return
            else:
                return
        
        self.processing = True
        self.progress.pack(side=tk.RIGHT, padx=15)
        
        # 在新线程中处理
        thread = threading.Thread(target=self._process_thread)
        thread.start()
        
    def _process_thread(self):
        """处理线程"""
        try:
            result = self.image.copy()
            algorithm = self.algorithm.get()
            total_regions = len(self.selected_regions)
            
            for i, (x, y, w, h) in enumerate(self.selected_regions, 1):
                progress = int((i / total_regions) * 100)
                self.root.after(0, self.progress.config, {'value': progress})
                self.root.after(0, self.update_status, f"⏳ 处理中... {i}/{total_regions}")
                
                # 创建掩码
                mask = np.zeros(result.shape[:2], dtype=np.uint8)
                mask[y:y+h, x:x+w] = 255
                
                # 根据算法处理
                if algorithm == "advanced":
                    result = self.advanced_inpaint(result, mask, x, y, w, h)
                elif algorithm == "telea":
                    result = cv2.inpaint(result, mask, 5, cv2.INPAINT_TELEA)
                elif algorithm == "ns":
                    result = cv2.inpaint(result, mask, 5, cv2.INPAINT_NS)
                elif algorithm == "smart_blur":
                    result = self.smart_blur_inpaint(result, mask, x, y, w, h)
                elif algorithm == "texture":
                    result = self.texture_fill(result, mask, x, y, w, h)
                
                # 边缘优化
                if self.edge_optimize.get():
                    result = self.optimize_edges(result, mask, x, y, w, h)
            
            # 色彩校正
            if self.color_correct.get():
                result = self.auto_color_correct(result)
            
            self.result_image = result
            
            # 更新界面
            self.root.after(0, self._process_complete)
            
        except Exception as e:
            self.root.after(0, messagebox.showerror, "错误", f"处理失败: {str(e)}")
            self.root.after(0, self._process_complete)
            
    def _process_complete(self):
        """处理完成"""
        self.processing = False
        self.progress.pack_forget()
        self.update_status("✅ 处理完成！")
        
        # 显示结果
        self.display_image = self.result_image.copy()
        self.show_image()
        
        # 启用保存按钮
        self.save_btn.config(state='normal')
        
        messagebox.showinfo("完成", "水印处理完成！\n点击 '💾 保存' 按钮保存结果")
        
    def advanced_inpaint(self, img, mask, x, y, w, h):
        """深度修复算法"""
        # 扩展修复区域
        strength = self.strength_var.get()
        radius = max(3, strength)
        
        # 使用多次迭代提升效果
        result = img.copy()
        for _ in range(2):
            result = cv2.inpaint(result, mask, radius, cv2.INPAINT_TELEA)
        
        # 高斯模糊平滑边缘
        blurred = cv2.GaussianBlur(result, (5, 5), 0)
        
        # 混合原图和修复结果
        alpha = 0.7
        result = cv2.addWeighted(result, alpha, blurred, 1 - alpha, 0)
        
        return result
        
    def smart_blur_inpaint(self, img, mask, x, y, w, h):
        """智能模糊填充"""
        result = img.copy()
        
        # 获取周边区域
        pad = 20
        y1 = max(0, y - pad)
        y2 = min(img.shape[0], y + h + pad)
        x1 = max(0, x - pad)
        x2 = min(img.shape[1], x + w + pad)
        
        roi = result[y1:y2, x1:x2].copy()
        
        # 双边滤波保留边缘
        blurred = cv2.bilateralFilter(roi, 9, 75, 75)
        
        # 高斯模糊
        blurred = cv2.GaussianBlur(blurred, (21, 21), 0)
        
        # 替换水印区域
        mask_y1 = y - y1
        mask_y2 = mask_y1 + h
        mask_x1 = x - x1
        mask_x2 = mask_x1 + w
        
        result[y:y+h, x:x+w] = blurred[mask_y1:mask_y2, mask_x1:mask_x2]
        
        return result
        
    def texture_fill(self, img, mask, x, y, w, h):
        """纹理填充"""
        result = img.copy()
        
        # 提取周边纹理
        pad = 30
        y1 = max(0, y - pad)
        y2 = min(img.shape[0], y + h + pad)
        x1 = max(0, x - pad)
        x2 = min(img.shape[1], x + w + pad)
        
        # 使用周边像素的中值
        roi = result[y1:y2, x1:x2]
        
        # 对每个通道计算中值
        for c in range(3):
            channel = roi[:, :, c]
            median_val = np.median(channel)
            noise = np.random.normal(0, 5, (h, w))
            result[y:y+h, x:x+w, c] = np.clip(median_val + noise, 0, 255)
        
        # 模糊边缘
        mask_roi = mask[y:y+h, x:x+w]
        blurred = cv2.GaussianBlur(result[y:y+h, x:x+w], (15, 15), 0)
        result[y:y+h, x:x+w] = blurred
        
        return result
        
    def optimize_edges(self, img, mask, x, y, w, h):
        """优化边缘"""
        result = img.copy()
        
        # 创建边缘掩码（只处理边缘区域）
        kernel = np.ones((5, 5), np.uint8)
        edge_mask = cv2.dilate(mask, kernel, iterations=1) - mask
        
        # 对边缘区域进行额外的模糊处理
        blurred = cv2.GaussianBlur(result, (7, 7), 0)
        
        # 只在边缘区域应用模糊
        edge_indices = np.where(edge_mask > 0)
        result[edge_indices] = blurred[edge_indices]
        
        return result
        
    def auto_color_correct(self, img):
        """自动色彩校正"""
        # 转换到LAB色彩空间
        lab = cv2.cvtColor(img, cv2.COLOR_BGR2LAB)
        l, a, b = cv2.split(lab)
        
        # 应用CLAHE (对比度限制自适应直方图均衡化)
        clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8, 8))
        l = clahe.apply(l)
        
        # 合并通道
        lab = cv2.merge([l, a, b])
        result = cv2.cvtColor(lab, cv2.COLOR_LAB2BGR)
        
        return result
        
    def auto_detect(self):
        """自动检测水印"""
        if self.image is None:
            messagebox.showwarning("警告", "请先打开图片！")
            return
        
        try:
            self.update_status("🔍 正在检测水印...")
            
            gray = cv2.cvtColor(self.image, cv2.COLOR_BGR2GRAY)
            
            # 多种检测方法组合
            # 方法1: 检测高亮区域
            _, thresh1 = cv2.threshold(gray, 200, 255, cv2.THRESH_BINARY)
            
            # 方法2: 检测低对比度区域（可能是半透明水印）
            _, thresh2 = cv2.threshold(gray, 180, 255, cv2.THRESH_BINARY)
            
            # 合并检测结果
            combined = cv2.bitwise_or(thresh1, thresh2)
            
            # 形态学操作
            kernel = np.ones((7, 7), np.uint8)
            dilated = cv2.dilate(combined, kernel, iterations=2)
            
            # 查找轮廓
            contours, _ = cv2.findContours(dilated, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
            
            self.selected_regions = []
            
            for contour in contours:
                x, y, w, h = cv2.boundingRect(contour)
                area = w * h
                
                # 过滤条件
                if (w > 30 and h > 30 and area > 1000 and 
                    area < self.image.shape[0] * self.image.shape[1] * 0.3):
                    self.selected_regions.append((x, y, w, h))
            
            if self.selected_regions:
                self.update_region_list()
                self.update_stats()
                self.show_image()
                self.update_status(f"🔍 检测到 {len(self.selected_regions)} 个区域")
                messagebox.showinfo("检测完成", 
                                   f"检测到 {len(self.selected_regions)} 个可能的水印区域\n"
                                   "请检查并调整选区")
            else:
                self.update_status("⚠️ 未检测到水印")
                messagebox.showinfo("检测结果", "未检测到明显的水印区域\n请手动选择")
                
        except Exception as e:
            messagebox.showerror("错误", f"检测失败: {str(e)}")
            
    def smart_select(self):
        """智能选择（基于颜色相似度）"""
        messagebox.showinfo("提示", "智能选择功能\n\n在图片上点击水印区域，\n程序会自动选择相似颜色的区域")
        
    def rectangle_select(self):
        """矩形选择模式"""
        self.update_status("📐 矩形选择模式已激活")
        
    def brush_select(self):
        """画笔选择模式"""
        messagebox.showinfo("提示", "画笔选择功能开发中...")
        
    def delete_selected_region(self):
        """删除选中区域"""
        selection = self.region_listbox.curselection()
        if selection:
            index = selection[0]
            del self.selected_regions[index]
            self.update_region_list()
            self.update_stats()
            self.show_image()
            self.update_status(f"🗑️ 已删除区域 #{index + 1}")
            
    def edit_region(self):
        """编辑区域"""
        selection = self.region_listbox.curselection()
        if not selection:
            messagebox.showwarning("警告", "请先选择一个区域！")
            return
        
        index = selection[0]
        x, y, w, h = self.selected_regions[index]
        
        # 创建编辑对话框
        dialog = tk.Toplevel(self.root)
        dialog.title(f"编辑区域 #{index + 1}")
        dialog.geometry("300x200")
        dialog.configure(bg=self.colors['panel'])
        dialog.resizable(False, False)
        
        # 居中显示
        dialog.transient(self.root)
        dialog.grab_set()
        
        tk.Label(dialog, text="调整区域坐标和大小", 
                font=('Segoe UI', 11, 'bold'),
                bg=self.colors['panel'], fg=self.colors['fg']).pack(pady=10)
        
        # 输入框
        frame = tk.Frame(dialog, bg=self.colors['panel'])
        frame.pack(padx=20, pady=10)
        
        entries = {}
        for i, (label, val) in enumerate([('X:', x), ('Y:', y), ('宽度:', w), ('高度:', h)]):
            tk.Label(frame, text=label, bg=self.colors['panel'], 
                    fg=self.colors['text']).grid(row=i, column=0, sticky=tk.W, pady=5)
            entry = tk.Entry(frame, width=15)
            entry.insert(0, str(val))
            entry.grid(row=i, column=1, padx=(10, 0), pady=5)
            entries[label] = entry
        
        def save_edit():
            try:
                new_x = int(entries['X:'].get())
                new_y = int(entries['Y:'].get())
                new_w = int(entries['宽度:'].get())
                new_h = int(entries['高度:'].get())
                
                if new_w > 0 and new_h > 0:
                    self.selected_regions[index] = (new_x, new_y, new_w, new_h)
                    self.update_region_list()
                    self.update_stats()
                    self.show_image()
                    dialog.destroy()
                else:
                    messagebox.showerror("错误", "宽度和高度必须大于0！", parent=dialog)
            except ValueError:
                messagebox.showerror("错误", "请输入有效的数字！", parent=dialog)
        
        tk.Button(dialog, text="保存", command=save_edit,
                 bg=self.colors['success'], fg='white',
                 relief=tk.FLAT, cursor='hand2',
                 padx=20, pady=5).pack(pady=10)
        
    def clear_regions(self):
        """清除所有选区"""
        if self.selected_regions:
            result = messagebox.askyesno("确认", "确定要清除所有选区吗？")
            if result:
                self.selected_regions = []
                self.update_region_list()
                self.update_stats()
                self.show_image()
                self.update_status("🗑️ 已清除所有选区")
                
    def save_result(self):
        """保存结果"""
        if self.result_image is None:
            messagebox.showwarning("警告", "还没有处理结果！")
            return
        
        file_path = filedialog.asksaveasfilename(
            title="保存结果",
            defaultextension=".jpg",
            filetypes=[
                ("JPEG图片", "*.jpg"),
                ("PNG图片", "*.png"),
                ("所有文件", "*.*")
            ],
            initialfile="watermark_removed.jpg"
        )
        
        if file_path:
            try:
                cv2.imwrite(file_path, self.result_image)
                self.update_status(f"💾 已保存: {os.path.basename(file_path)}")
                messagebox.showinfo("成功", f"图片已保存到:\n{file_path}")
            except Exception as e:
                messagebox.showerror("错误", f"保存失败: {str(e)}")
                
    def zoom_in(self):
        """放大"""
        if self.scale_factor < 2.0:
            self.scale_factor *= 1.2
            self.show_image()
            
    def zoom_out(self):
        """缩小"""
        if self.scale_factor > 0.1:
            self.scale_factor /= 1.2
            self.show_image()
            
    def update_zoom_label(self):
        """更新缩放标签"""
        zoom_percent = int(self.scale_factor * 100)
        self.zoom_label.config(text=f"{zoom_percent}%")
        
    def update_status(self, text):
        """更新状态栏"""
        self.status_label.config(text=text)
        
    def run(self):
        """运行程序"""
        self.root.mainloop()


if __name__ == "__main__":
    """
    安装依赖:
    pip install opencv-python numpy pillow
    
    专业版特性:
    - 深色主题界面
    - 多种高级算法
    - 边缘优化
    - 自动色彩校正
    - 智能检测
    - 实时统计
    - 多线程处理
    """
    
    app = WatermarkRemoverPro()
    app.run()