from .cli import main
import time

if __name__ == "__main__":
    start = time.perf_counter()
    exit_code = main()          
    end = time.perf_counter()
    print(f"运行耗时：{end-start:.6f} 秒")
    raise SystemExit(exit_code)  
