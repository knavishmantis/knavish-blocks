#!/usr/bin/env python3
"""
Generate 20 different off-white block textures for Minecraft mod
Each texture is a 16x16 PNG with a solid off-white color
"""

from PIL import Image
import os

# Define 20 different off-white solid colors
BLOCK_COLORS = {
    'alabaster_block': (250, 250, 250),
    'pearl_block': (250, 250, 248),
    'cream_block': (255, 253, 245),
    'ivory_block': (255, 255, 240),
    'vanilla_block': (255, 250, 240),
    'eggshell_block': (252, 250, 242),
    'linen_block': (250, 245, 237),
    'seashell_block': (255, 245, 238),
    'bone_block': (248, 246, 237),
    'cotton_block': (255, 254, 250),
    'porcelain_block': (250, 248, 245),
    'champagne_block': (250, 245, 232),
    'parchment_block': (252, 248, 237),
    'coconut_block': (248, 245, 237),
    'canvas_block': (250, 247, 240),
    'bisque_block': (255, 248, 237),
    'ecru_block': (245, 240, 230),
    'magnolia_block': (252, 250, 245),
    'almond_block': (250, 243, 233),
    'ghost_block': (248, 248, 255),
    'mint_block': (220, 245, 220),      # Even more pronounced green
    'sand_block': (240, 225, 200),      # Even more pronounced brown
    'blush_block': (245, 220, 220)      # Even more pronounced red
}

def generate_texture(block_name, color, output_dir):
    """Generate a single 16x16 solid color texture"""
    img = Image.new('RGB', (16, 16), color)
    
    output_path = os.path.join(output_dir, f"{block_name}.png")
    img.save(output_path, 'PNG')
    print(f"Generated {block_name}.png")

def main():
    # Get the output directory from command line or use default
    output_dir = "/home/quinncaverly/IdeaProjects/KnavishMantisProjects/Off-White-Blocks/src/main/resources/assets/off-white-blocks/textures/block"
    
    # Create output directory if it doesn't exist
    os.makedirs(output_dir, exist_ok=True)
    
    print("Generating 23 off-white block textures...")
    
    # Generate all textures
    for block_name, colors in BLOCK_COLORS.items():
        generate_texture(block_name, colors, output_dir)
    
    print(f"\nSuccessfully generated {len(BLOCK_COLORS)} textures in {output_dir}")

if __name__ == "__main__":
    main()
