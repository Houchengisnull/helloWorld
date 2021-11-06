from tkinter import *


class Application(Frame):
    cube = [[[1, 1], [1, 1]]
        , [[2, 2], [2, 2]]
        , [[3, 3], [3, 3]]
        , [[4, 4], [4, 4]]
        , [[5, 5], [5, 5]]
        , [[6, 6], [6, 6]]
            ]
    label_frames = []

    def __init__(self, master=None):
        frame = Frame.__init__(self, master)
        cube = self.cube
        label_frames = self.label_frames
        # side
        # 1
        label_frame_1 = Frame(frame)
        label_frame_1.place(relx=0.4, rely=0.3, relwidth=0.2, relheight=0.2)
        label_frames.append(label_frame_1)
        # 2
        label_frame_2 = Frame(frame)
        label_frame_2.place(relx=0.2, rely=0.3, relwidth=0.2, relheight=0.2)
        label_frames.append(label_frame_2)
        # 3
        label_frame_3 = Frame(frame)
        label_frame_3.place(relx=0.4, rely=0.1, relwidth=0.2, relheight=0.2)
        label_frames.append(label_frame_3)
        # 4
        label_frame_4 = Frame(frame)
        label_frame_4.place(relx=0.6, rely=0.3, relwidth=0.2, relheight=0.2)
        label_frames.append(label_frame_4)
        # 5
        label_frame_5 = Frame(frame)
        label_frame_5.place(relx=0.4, rely=0.5, relwidth=0.2, relheight=0.2)
        label_frames.append(label_frame_5)
        # 6
        label_frame_6 = Frame(frame)
        label_frame_6.place(relx=0.4, rely=0.7, relwidth=0.2, relheight=0.2)
        label_frames.append(label_frame_6)
        # button group
        button_1 = Button(frame, text='>>')
        button_1.bind('<Button-1>', func=self._click)
        button_1.place(relx=0.8, rely=0.33, relwidth=0.15, relheight=0.05)
        button_2 = Button(frame, text='>>')
        button_2.place(relx=0.8, rely=0.42, relwidth=0.15, relheight=0.05)
        button_3 = Button(frame, text='<<')
        button_3.place(relx=0.05, rely=0.33, relwidth=0.15, relheight=0.05)
        button_4 = Button(frame, text='<<')
        button_4.place(relx=0.05, rely=0.42, relwidth=0.15, relheight=0.05)
        button_5 = Button(frame, text='⬆')
        button_5.place(relx=0.43, rely=0.01, relwidth=0.05, relheight=0.10)
        button_6 = Button(frame, text='⬆')
        button_6.place(relx=0.52, rely=0.01, relwidth=0.05, relheight=0.10)
        for i in range(len(cube)):
            side = cube[i]
            for j in range(len(side)):
                self.make_side(label_frames[i], side[j][0])
                self.make_side(label_frames[i], side[j][1])

    @staticmethod
    def get_color(color_code):
        if color_code == 1:
            return 'pink'
        if color_code == 2:
            return 'purple'
        if color_code == 3:
            return '#ACD6FF'
        if color_code == 4:
            return 'brown'
        if color_code == 5:
            return '#0080FF'
        if color_code == 6:
            return 'orange'
        return 'white'

    def make_side(self, parent, val):
        label_1 = Label(parent, text=val, background=Application.get_color(val))
        label_2 = Label(parent, text=val, background=Application.get_color(val))
        label_3 = Label(parent, text=val, background=Application.get_color(val))
        label_4 = Label(parent, text=val, background=Application.get_color(val))
        label_1.place(relx=0.1, rely=0.1, relwidth=0.35, relheight=0.35)
        label_2.place(relx=0.55, rely=0.1, relwidth=0.35, relheight=0.35)
        label_3.place(relx=0.1, rely=0.55, relwidth=0.35, relheight=0.35)
        label_4.place(relx=0.55, rely=0.55, relwidth=0.35, relheight=0.35)

    '''
        1、按照顺时针、逆时针划分每个面分别有两种行为，又因为有六个面，共十二种行为
    '''
    def _click(self, event):
        print('click')
        # self.cube =


app = Application()
app.master.minsize(500, 500)
app.master.title('二阶魔方')
app.mainloop()
